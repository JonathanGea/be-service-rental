package com.gea.app.vehicle;

import com.gea.app.shared.model.dto.ApiResponse;
import com.gea.app.vehicle.dto.VehicleTypeRequest;
import com.gea.app.vehicle.dto.VehicleTypeResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Vehicle type endpoints for Admin/Staff.
 */
@RestController
@RequestMapping("/api/vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleTypeResponse>>> getVehicleTypes() {
        var data = vehicleTypeService.getVehicleTypes();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleTypeResponse>> getVehicleType(@PathVariable UUID id) {
        var data = vehicleTypeService.getVehicleType(id);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleTypeResponse>> createVehicleType(
            @Valid @RequestBody VehicleTypeRequest request) {
        var data = vehicleTypeService.createVehicleType(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleTypeResponse>> updateVehicleType(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleTypeRequest request) {
        var data = vehicleTypeService.updateVehicleType(id, request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
