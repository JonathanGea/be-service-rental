package com.gea.app.availability.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Availability status for a specific date.
 */
@Getter
@AllArgsConstructor
public class AvailabilityDateStatus {
    private LocalDate date;
    private String status;
}
