package com.gea.app.availability.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for availability calendar.
 */
@Getter
@AllArgsConstructor
public class AvailabilityCalendarResponse {
    private UUID vehicleId;
    private List<AvailabilityDateStatus> dates;
}
