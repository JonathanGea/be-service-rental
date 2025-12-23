package com.gea.app.rental.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for rental endpoints.
 */
@Getter
@AllArgsConstructor
public class RentalResponse {
    private UUID id;
    private UUID vehicleId;
    private String renterName;
    private String renterPhone;
    private String renterAddress;
    private String renterIdNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private String pickupLocation;
    private String returnLocation;
    private Long priceTotal;
    private String status;
    private String notes;
    private String conditionNotes;
}
