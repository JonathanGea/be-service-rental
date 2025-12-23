package com.gea.app.vehicle;

import com.gea.app.shared.model.dto.ApiResponse;
import com.gea.app.vehicle.dto.VehicleRequest;
import com.gea.app.vehicle.dto.VehicleResponse;
import com.gea.app.vehicle.dto.VehicleStatusRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Vehicle endpoints for Admin/Staff.
 */
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(@Valid @RequestBody VehicleRequest request) {
        var data = vehicleService.createVehicle(request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getVehicles(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false, name = "q") String query) {
        var data = vehicleService.getVehicles(status, categoryId, query);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicle(@PathVariable UUID id) {
        var data = vehicleService.getVehicle(id);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleRequest request) {
        var data = vehicleService.updateVehicle(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> deleteVehicle(@PathVariable UUID id) {
        var data = vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleStatusRequest request) {
        var data = vehicleService.updateStatus(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }
}
