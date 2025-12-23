package com.gea.app.rental;

import com.gea.app.shared.model.dto.ApiResponse;
import com.gea.app.rental.dto.RentalRequest;
import com.gea.app.rental.dto.RentalResponse;
import com.gea.app.rental.dto.RentalUpdateRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rental endpoints for Admin/Staff.
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<ApiResponse<RentalResponse>> createRental(@Valid @RequestBody RentalRequest request) {
        var data = rentalService.createRental(request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RentalResponse>>> getRentals(
            @RequestParam(required = false) UUID vehicleId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        var data = rentalService.getRentals(vehicleId, status, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RentalResponse>> getRental(@PathVariable UUID id) {
        var data = rentalService.getRental(id);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RentalResponse>> updateRental(
            @PathVariable UUID id,
            @Valid @RequestBody RentalUpdateRequest request) {
        var data = rentalService.updateRental(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<ApiResponse<RentalResponse>> returnRental(@PathVariable UUID id) {
        var data = rentalService.returnRental(id);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }
}
