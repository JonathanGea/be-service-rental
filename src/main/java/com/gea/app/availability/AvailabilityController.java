package com.gea.app.availability;

import com.gea.app.availability.dto.AvailabilityCalendarResponse;
import com.gea.app.shared.model.dto.ApiResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Availability calendar endpoints for Admin/Staff.
 */
@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<AvailabilityCalendarResponse>> getCalendar(
            @RequestParam UUID vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        var data = availabilityService.getCalendar(vehicleId, startDate, endDate);
        return ResponseEntity.ok(new ApiResponse<>(true, data));
    }
}
