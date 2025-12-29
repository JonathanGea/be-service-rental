package com.gea.app.vehicle;

import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.dto.VehicleRequest;
import com.gea.app.vehicle.dto.VehicleResponse;
import com.gea.app.vehicle.dto.VehicleStatusRequest;
import com.gea.app.vehicle.entity.Brand;
import com.gea.app.vehicle.entity.Vehicle;
import com.gea.app.vehicle.entity.VehicleType;
import com.gea.app.vehicle.enum_.VehicleStatus;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Business logic for vehicle endpoints.
 */
@Service
@RequiredArgsConstructor
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;
    private final BrandRepository brandRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleResponse createVehicle(VehicleRequest request) {
        VehicleStatus status = parseStatusOrThrow(request.getStatus());
        validateManualStatus(status);
        Brand brand = resolveBrand(request.getBrandId());
        VehicleType vehicleType = resolveVehicleType(request.getVehicleTypeId());

        Vehicle vehicle = Vehicle.builder()
                .name(request.getName())
                .brand(brand)
                .vehicleType(vehicleType)
                .year(request.getYear())
                .transmission(request.getTransmission())
                .capacity(request.getCapacity())
                .pricePerDay(request.getPricePerDay())
                .description(request.getDescription())
                .status(status)
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle created id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public List<VehicleResponse> getVehicles(String status, String query) {
        VehicleStatus statusFilter = parseStatusOrNull(status);
        if (status != null && statusFilter == null) {
            throw invalidStatus(status);
        }

        Specification<Vehicle> spec = Specification.where(null);
        if (statusFilter != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("status"), statusFilter));
        }
        if (StringUtils.hasText(query)) {
            String like = "%" + query.toLowerCase() + "%";
            spec = spec.and((root, cq, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("brand").get("name")), like),
                    cb.like(cb.lower(root.get("vehicleType").get("name")), like)
            ));
        }

        return vehicleRepository.findAll(spec).stream()
                .map(this::toResponse)
                .toList();
    }

    public VehicleResponse getVehicle(UUID id) {
        Vehicle vehicle = getByIdOrThrow(id);
        return toResponse(vehicle);
    }

    public VehicleResponse updateVehicle(UUID id, VehicleRequest request) {
        Vehicle vehicle = getByIdOrThrow(id);
        VehicleStatus newStatus = parseStatusOrThrow(request.getStatus());
        validateManualStatus(newStatus);
        validateStatusTransition(vehicle.getStatus(), newStatus);
        Brand brand = resolveBrand(request.getBrandId());
        VehicleType vehicleType = resolveVehicleType(request.getVehicleTypeId());

        vehicle.setName(request.getName());
        vehicle.setBrand(brand);
        vehicle.setVehicleType(vehicleType);
        vehicle.setYear(request.getYear());
        vehicle.setTransmission(request.getTransmission());
        vehicle.setCapacity(request.getCapacity());
        vehicle.setPricePerDay(request.getPricePerDay());
        vehicle.setDescription(request.getDescription());
        vehicle.setStatus(newStatus);

        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle updated id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    public Map<String, Object> deleteVehicle(UUID id) {
        Vehicle vehicle = getByIdOrThrow(id);
        vehicleRepository.delete(vehicle);
        log.info("Vehicle deleted id={}", id);
        return Map.of("deleted", true);
    }

    public VehicleResponse updateStatus(UUID id, VehicleStatusRequest request) {
        Vehicle vehicle = getByIdOrThrow(id);
        VehicleStatus newStatus = parseStatusOrThrow(request.getStatus());
        validateManualStatus(newStatus);
        validateStatusTransition(vehicle.getStatus(), newStatus);

        vehicle.setStatus(newStatus);
        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle status updated id={} status={}", saved.getId(), saved.getStatus());
        return toResponse(saved);
    }

    private Vehicle getByIdOrThrow(UUID id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya kendaraan dengan ID '" + id + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Vehicle", "identifier", id.toString()))
                        .build()));
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getName(),
                vehicle.getBrand().getId(),
                vehicle.getBrand().getName(),
                vehicle.getVehicleType().getId(),
                vehicle.getVehicleType().getName(),
                vehicle.getYear(),
                vehicle.getTransmission(),
                vehicle.getCapacity(),
                vehicle.getPricePerDay(),
                vehicle.getDescription(),
                vehicle.getStatus().getValue()
        );
    }

    private Brand resolveBrand(UUID brandId) {
        if (brandId == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom 'brandId' tidak boleh kosong.")
                    .field("brandId")
                    .build());
        }
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya brand dengan ID '" + brandId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "Brand", "identifier", brandId.toString()))
                        .build()));
    }

    private VehicleType resolveVehicleType(UUID vehicleTypeId) {
        if (vehicleTypeId == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom 'vehicleTypeId' tidak boleh kosong.")
                    .field("vehicleTypeId")
                    .build());
        }
        return vehicleTypeRepository.findById(vehicleTypeId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya vehicle type dengan ID '" + vehicleTypeId + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "VehicleType", "identifier", vehicleTypeId.toString()))
                        .build()));
    }

    private VehicleStatus parseStatusOrThrow(String raw) {
        VehicleStatus status = parseStatusOrNull(raw);
        if (status == null) {
            throw invalidStatus(raw);
        }
        return status;
    }

    private VehicleStatus parseStatusOrNull(String raw) {
        if (!StringUtils.hasText(raw)) {
            return null;
        }
        return VehicleStatus.fromValue(raw);
    }

    private ApiException invalidStatus(String raw) {
        return new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                .code("VALIDATION_INVALID")
                .message("Status '" + raw + "' tidak valid.")
                .field("status")
                .details(Map.of("expected", "available|unavailable|maintenance|rented"))
                .build());
    }

    private void validateManualStatus(VehicleStatus status) {
        if (status == VehicleStatus.RENTED) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Status 'rented' dikelola sistem.")
                    .field("status")
                    .details(Map.of("forbidden", "rented"))
                    .build());
        }
    }

    private void validateStatusTransition(VehicleStatus current, VehicleStatus target) {
        if (target == VehicleStatus.MAINTENANCE && current != VehicleStatus.AVAILABLE
                && current != VehicleStatus.MAINTENANCE) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_INVALID")
                    .message("Status 'maintenance' hanya bisa dari 'available'.")
                    .field("status")
                    .details(Map.of("from", current.getValue(), "to", target.getValue()))
                    .build());
        }
    }
}
