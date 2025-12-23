package com.gea.app.public_;

import com.gea.app.public_.dto.PublicVehicleDetailResponse;
import com.gea.app.public_.dto.PublicVehiclesResponse;
import com.gea.app.shared.model.dto.ApiResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public endpoints for landing page.
 */
@RestController
@RequestMapping("/api/public/vehicles")
@RequiredArgsConstructor
public class PublicVehicleController {

    private final PublicVehicleService publicVehicleService;

    @GetMapping
    public ResponseEntity<ApiResponse<PublicVehiclesResponse>> getVehicles(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, name = "q") String query,
            @RequestParam(required = false) UUID categoryId) {
        var data = publicVehicleService.getVehicles(startDate, endDate, query, categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PublicVehicleDetailResponse>> getVehicle(
            @PathVariable UUID id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        var data = publicVehicleService.getVehicle(id, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }
}
