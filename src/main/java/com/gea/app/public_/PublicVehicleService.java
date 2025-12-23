package com.gea.app.public_;

import com.gea.app.public_.dto.PublicVehicleDetailResponse;
import com.gea.app.public_.dto.PublicVehicleItemResponse;
import com.gea.app.public_.dto.PublicVehiclesResponse;
import com.gea.app.rental.RentalRepository;
import com.gea.app.rental.entity.Rental;
import com.gea.app.rental.enum_.RentalStatus;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.VehicleRepository;
import com.gea.app.vehicle.entity.Vehicle;
import com.gea.app.vehicle.enum_.VehicleStatus;
import com.gea.app.vehicle.photo.VehiclePhotoRepository;
import com.gea.app.vehicle.photo.entity.VehiclePhoto;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Public vehicle endpoints for landing page.
 */
@Service
@RequiredArgsConstructor
public class PublicVehicleService {

    private static final Set<RentalStatus> BLOCKING_STATUSES = Set.of(
            RentalStatus.PENDING,
            RentalStatus.ACTIVE
    );

    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final VehiclePhotoRepository vehiclePhotoRepository;

    @Value("${app.whatsapp.phone:}")
    private String whatsappPhone;

    public PublicVehiclesResponse getVehicles(LocalDate startDate, LocalDate endDate, String query, UUID categoryId) {
        validateDateRangeIfProvided(startDate, endDate);

        Specification<Vehicle> spec = Specification.where(null);
        if (categoryId != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("categoryId"), categoryId));
        }
        if (StringUtils.hasText(query)) {
            String like = "%" + query.toLowerCase() + "%";
            spec = spec.and((root, cq, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("brand")), like),
                    cb.like(cb.lower(root.get("type")), like)
            ));
        }

        List<Vehicle> vehicles = vehicleRepository.findAll(spec);
        List<PublicVehicleItemResponse> items = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            List<Rental> rentals = List.of();
            if (startDate != null && endDate != null) {
                rentals = rentalRepository.findByVehicleIdAndStatusInAndDateRange(
                        vehicle.getId(), BLOCKING_STATUSES, startDate, endDate
                );
            }
            String status = resolveStatus(vehicle, rentals, startDate, endDate);
            LocalDate nextAvailableDate = resolveNextAvailableDate(vehicle, rentals, startDate, endDate, status);
            String thumbnailUrl = resolveThumbnail(vehicle.getId());

            items.add(new PublicVehicleItemResponse(
                    vehicle.getId(),
                    vehicle.getName(),
                    vehicle.getPricePerDay(),
                    status,
                    nextAvailableDate,
                    thumbnailUrl
            ));
        }

        return new PublicVehiclesResponse(items);
    }

    public PublicVehicleDetailResponse getVehicle(UUID vehicleId, LocalDate startDate, LocalDate endDate) {
        validateDateRangeIfProvided(startDate, endDate);
        Vehicle vehicle = getVehicleOrThrow(vehicleId);

        List<Rental> rentals = List.of();
        if (startDate != null && endDate != null) {
            rentals = rentalRepository.findByVehicleIdAndStatusInAndDateRange(
                    vehicleId, BLOCKING_STATUSES, startDate, endDate
            );
        }

        List<LocalDate> availableDates = new ArrayList<>();
        if (startDate != null && endDate != null && isVehicleAvailableForCalendar(vehicle)) {
            LocalDate cursor = startDate;
            while (!cursor.isAfter(endDate)) {
                LocalDate current = cursor;
                boolean rented = rentals.stream().anyMatch(rental ->
                        !current.isBefore(rental.getStartDate()) && !current.isAfter(rental.getEndDate())
                );
                if (!rented) {
                    availableDates.add(cursor);
                }
                cursor = cursor.plusDays(1);
            }
        }

        List<String> images = vehiclePhotoRepository.findByVehicleIdOrderByOrderIndexAsc(vehicleId)
                .stream()
                .map(VehiclePhoto::getUrl)
                .toList();

        String status = resolveStatus(vehicle, rentals, startDate, endDate);

        return new PublicVehicleDetailResponse(
                vehicle.getId(),
                vehicle.getName(),
                vehicle.getBrand(),
                vehicle.getType(),
                vehicle.getYear(),
                vehicle.getTransmission(),
                vehicle.getCapacity(),
                vehicle.getPricePerDay(),
                status,
                availableDates,
                images,
                buildWhatsAppLink(vehicle, startDate, endDate)
        );
    }

    private String resolveStatus(Vehicle vehicle, List<Rental> rentals, LocalDate startDate, LocalDate endDate) {
        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE) {
            return VehicleStatus.MAINTENANCE.getValue();
        }
        if (vehicle.getStatus() == VehicleStatus.UNAVAILABLE) {
            return VehicleStatus.UNAVAILABLE.getValue();
        }
        if (startDate == null || endDate == null) {
            return vehicle.getStatus().getValue();
        }
        boolean rented = rentals.stream().anyMatch(rental ->
                !endDate.isBefore(rental.getStartDate()) && !startDate.isAfter(rental.getEndDate())
        );
        return rented ? VehicleStatus.RENTED.getValue() : VehicleStatus.AVAILABLE.getValue();
    }

    private LocalDate resolveNextAvailableDate(
            Vehicle vehicle,
            List<Rental> rentals,
            LocalDate startDate,
            LocalDate endDate,
            String status) {
        if (startDate == null || endDate == null) {
            return resolveNextAvailableDateWithoutRange(vehicle, status);
        }
        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE || vehicle.getStatus() == VehicleStatus.UNAVAILABLE) {
            return null;
        }
        if (!VehicleStatus.RENTED.getValue().equals(status)) {
            return startDate;
        }
        Optional<LocalDate> lastRentalEnd = rentals.stream()
                .map(Rental::getEndDate)
                .max(LocalDate::compareTo);
        return lastRentalEnd.map(date -> date.plusDays(1)).orElse(startDate);
    }

    private LocalDate resolveNextAvailableDateWithoutRange(Vehicle vehicle, String status) {
        if (vehicle.getStatus() == VehicleStatus.MAINTENANCE || vehicle.getStatus() == VehicleStatus.UNAVAILABLE) {
            return null;
        }
        if (!VehicleStatus.RENTED.getValue().equals(status)) {
            return LocalDate.now();
        }
        LocalDate today = LocalDate.now();
        LocalDate lastRentalEnd = rentalRepository.findMaxEndDateByVehicleIdAndStatusInAndEndDateAfter(
                vehicle.getId(), BLOCKING_STATUSES, today
        );
        return lastRentalEnd != null ? lastRentalEnd.plusDays(1) : null;
    }

    private String resolveThumbnail(UUID vehicleId) {
        List<VehiclePhoto> photos = vehiclePhotoRepository.findByVehicleIdOrderByOrderIndexAsc(vehicleId);
        if (photos.isEmpty()) {
            return null;
        }
        return photos.get(0).getUrl();
    }

    private boolean isVehicleAvailableForCalendar(Vehicle vehicle) {
        return vehicle.getStatus() != VehicleStatus.MAINTENANCE && vehicle.getStatus() != VehicleStatus.UNAVAILABLE;
    }

    private String buildWhatsAppLink(Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        if (!StringUtils.hasText(whatsappPhone)) {
            return null;
        }
        StringBuilder message = new StringBuilder("Halo, saya ingin sewa ")
                .append(vehicle.getName());
        if (startDate != null && endDate != null) {
            message.append(" tanggal ")
                    .append(startDate)
                    .append(" sampai ")
                    .append(endDate);
        }
        String encoded = URLEncoder.encode(message.toString(), StandardCharsets.UTF_8);
        return "https://wa.me/" + whatsappPhone + "?text=" + encoded;
    }

    private void validateDateRangeIfProvided(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return;
        }
        if (startDate == null || endDate == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Tanggal mulai dan selesai wajib diisi.")
                    .field("startDate")
                    .details(Map.of("required", List.of("startDate", "endDate")))
                    .build());
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

    private Vehicle getVehicleOrThrow(UUID vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya kendaraan dengan ID '" + vehicleId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Vehicle", "identifier", vehicleId.toString()))
                        .build()));
    }
}
