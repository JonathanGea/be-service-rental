package com.gea.app.vehicle;

import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.vehicle.dto.VehicleTypeRequest;
import com.gea.app.vehicle.dto.VehicleTypeResponse;
import com.gea.app.vehicle.entity.VehicleType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Business logic for vehicle type endpoints.
 */
@Service
@RequiredArgsConstructor
public class VehicleTypeService {

    private static final Logger log = LoggerFactory.getLogger(VehicleTypeService.class);

    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeResponse createVehicleType(VehicleTypeRequest request) {
        String name = normalizeName(request.getName(), "name");
        if (vehicleTypeRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, ApiError.builder()
                    .code("RESOURCE_ALREADY_EXISTS")
                    .message("Vehicle type '" + name + "' sudah terdaftar.")
                    .field("name")
                    .build());
        }
        VehicleType saved = vehicleTypeRepository.save(VehicleType.builder().name(name).build());
        log.info("Vehicle type created id={}", saved.getId());
        return new VehicleTypeResponse(saved.getId(), saved.getName());
    }

    public List<VehicleTypeResponse> getVehicleTypes() {
        return vehicleTypeRepository.findAll()
                .stream()
                .map(type -> new VehicleTypeResponse(type.getId(), type.getName()))
                .toList();
    }

    public VehicleTypeResponse getVehicleType(UUID id) {
        VehicleType vehicleType = getVehicleTypeOrThrow(id);
        return new VehicleTypeResponse(vehicleType.getId(), vehicleType.getName());
    }

    public VehicleTypeResponse updateVehicleType(UUID id, VehicleTypeRequest request) {
        VehicleType vehicleType = getVehicleTypeOrThrow(id);
        String name = normalizeName(request.getName(), "name");
        vehicleTypeRepository.findByNameIgnoreCase(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ApiException(HttpStatus.CONFLICT, ApiError.builder()
                            .code("RESOURCE_ALREADY_EXISTS")
                            .message("Vehicle type '" + name + "' sudah terdaftar.")
                            .field("name")
                            .build());
                });
        vehicleType.setName(name);
        VehicleType saved = vehicleTypeRepository.save(vehicleType);
        log.info("Vehicle type updated id={}", saved.getId());
        return new VehicleTypeResponse(saved.getId(), saved.getName());
    }

    private VehicleType getVehicleTypeOrThrow(UUID id) {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, ApiError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .message("Sumber daya vehicle type dengan ID '" + id + "' tidak ditemukan.")
                        .details(Map.of("resourceType", "VehicleType", "identifier", id.toString()))
                        .build()));
    }

    private String normalizeName(String raw, String field) {
        String name = raw != null ? raw.trim() : "";
        if (!StringUtils.hasText(name)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom '" + field + "' tidak boleh kosong.")
                    .field(field)
                    .build());
        }
        return name;
    }
}
