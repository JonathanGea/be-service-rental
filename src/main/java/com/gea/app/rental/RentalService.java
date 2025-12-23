package com.gea.app.rental;

import com.gea.app.rental.dto.RentalRequest;
import com.gea.app.rental.dto.RentalResponse;
import com.gea.app.rental.dto.RentalUpdateRequest;
import com.gea.app.rental.entity.Rental;
import com.gea.app.rental.enum_.RentalStatus;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.VehicleRepository;
import com.gea.app.vehicle.entity.Vehicle;
import com.gea.app.vehicle.enum_.VehicleStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Business logic for rental endpoints.
 */
@Service
@RequiredArgsConstructor
public class RentalService {

    private static final Set<RentalStatus> OVERLAP_STATUSES = Set.of(
            RentalStatus.PENDING,
            RentalStatus.ACTIVE
    );

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalResponse createRental(RentalRequest request) {
        Vehicle vehicle = getVehicleOrThrow(request.getVehicleId());
        ensureVehicleNotInMaintenance(vehicle);
        validateDateRange(request.getStartDate(), request.getEndDate());
        ensureNoOverlap(vehicle.getId(), request.getStartDate(), request.getEndDate(), null);

        Rental rental = Rental.builder()
                .vehicle(vehicle)
                .renterName(request.getRenterName())
                .renterPhone(request.getRenterPhone())
                .renterAddress(request.getRenterAddress())
                .renterIdNumber(request.getRenterIdNumber())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .pickupLocation(request.getPickupLocation())
                .returnLocation(request.getReturnLocation())
                .priceTotal(request.getPriceTotal())
                .status(RentalStatus.ACTIVE)
                .notes(request.getNotes())
                .build();

        Rental saved = rentalRepository.save(rental);
        if (vehicle.getStatus() != VehicleStatus.RENTED) {
            vehicle.setStatus(VehicleStatus.RENTED);
            vehicleRepository.save(vehicle);
        }
        return toResponse(saved);
    }

    public List<RentalResponse> getRentals(UUID vehicleId, String status, LocalDate startDate, LocalDate endDate) {
        RentalStatus statusFilter = parseStatusOrNull(status);
        if (status != null && statusFilter == null) {
            throw invalidStatus(status);
        }

        Specification<Rental> spec = Specification.where(null);
        if (vehicleId != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("vehicle").get("id"), vehicleId));
        }
        if (statusFilter != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("status"), statusFilter));
        }
        if (startDate != null) {
            spec = spec.and((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("endDate"), endDate));
        }

        return rentalRepository.findAll(spec).stream()
                .map(this::toResponse)
                .toList();
    }

    public RentalResponse getRental(UUID id) {
        Rental rental = getRentalOrThrow(id);
        return toResponse(rental);
    }

    public RentalResponse updateRental(UUID id, RentalUpdateRequest request) {
        Rental rental = getRentalOrThrow(id);
        if (rental.getStatus() == RentalStatus.COMPLETED || rental.getStatus() == RentalStatus.CANCELLED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Rental yang sudah selesai tidak dapat diubah.")
                    .build());
        }

        LocalDate start = request.getStartDate() != null ? request.getStartDate() : rental.getStartDate();
        LocalDate end = request.getEndDate() != null ? request.getEndDate() : rental.getEndDate();
        validateDateRange(start, end);
        ensureNoOverlap(rental.getVehicle().getId(), start, end, rental.getId());

        if (StringUtils.hasText(request.getRenterName())) {
            rental.setRenterName(request.getRenterName());
        }
        if (StringUtils.hasText(request.getRenterPhone())) {
            rental.setRenterPhone(request.getRenterPhone());
        }
        if (StringUtils.hasText(request.getRenterAddress())) {
            rental.setRenterAddress(request.getRenterAddress());
        }
        if (StringUtils.hasText(request.getRenterIdNumber())) {
            rental.setRenterIdNumber(request.getRenterIdNumber());
        }
        if (request.getStartDate() != null) {
            rental.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            rental.setEndDate(request.getEndDate());
        }
        if (request.getPickupLocation() != null) {
            rental.setPickupLocation(request.getPickupLocation());
        }
        if (request.getReturnLocation() != null) {
            rental.setReturnLocation(request.getReturnLocation());
        }
        if (request.getPriceTotal() != null) {
            rental.setPriceTotal(request.getPriceTotal());
        }
        if (request.getNotes() != null) {
            rental.setNotes(request.getNotes());
        }

        Rental saved = rentalRepository.save(rental);
        return toResponse(saved);
    }

    public RentalResponse returnRental(UUID id) {
        Rental rental = getRentalOrThrow(id);
        if (rental.getStatus() == RentalStatus.COMPLETED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Rental sudah dikembalikan.")
                    .build());
        }

        rental.setStatus(RentalStatus.COMPLETED);
        Rental saved = rentalRepository.save(rental);

        Vehicle vehicle = rental.getVehicle();
        if (vehicle.getStatus() == VehicleStatus.RENTED) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            vehicleRepository.save(vehicle);
        }
        return toResponse(saved);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return;
        }
        if (startDate.isAfter(endDate)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Tanggal mulai tidak boleh lebih dari tanggal selesai.")
                    .field("startDate")
                    .details(Map.of("startDate", startDate.toString(), "endDate", endDate.toString()))
                    .build());
        }
    }

    private void ensureNoOverlap(UUID vehicleId, LocalDate startDate, LocalDate endDate, UUID excludeId) {
        boolean overlap = rentalRepository.existsOverlappingRental(vehicleId, startDate, endDate, OVERLAP_STATUSES, excludeId);
        if (overlap) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Tanggal rental bertabrakan dengan rental lain.")
                    .details(Map.of("vehicleId", vehicleId.toString()))
                    .build());
        }
    }

    private void ensureVehicleNotInMaintenance(Vehicle vehicle) {
        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Kendaraan sedang maintenance.")
                    .details(Map.of("status", vehicle.getStatus().getValue()))
                    .build());
        }
    }

    private Vehicle getVehicleOrThrow(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya kendaraan dengan ID '" + vehicleId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Vehicle", "identifier", vehicleId.toString()))
                        .build()));
    }

    private Rental getRentalOrThrow(UUID id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya rental dengan ID '" + id + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Rental", "identifier", id.toString()))
                        .build()));
    }

    private RentalStatus parseStatusOrNull(String raw) {
        if (!StringUtils.hasText(raw)) {
            return null;
        }
        return RentalStatus.fromValue(raw);
    }

    private ApiException invalidStatus(String raw) {
        return new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                .code("VALIDATION_INVALID")
                .message("Status '" + raw + "' tidak valid.")
                .field("status")
                .details(Map.of("expected", "pending|active|completed|cancelled"))
                .build());
    }

    private RentalResponse toResponse(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getVehicle().getId(),
                rental.getRenterName(),
                rental.getRenterPhone(),
                rental.getRenterAddress(),
                rental.getRenterIdNumber(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getPickupLocation(),
                rental.getReturnLocation(),
                rental.getPriceTotal(),
                rental.getStatus().getValue(),
                rental.getNotes()
        );
    }
}
