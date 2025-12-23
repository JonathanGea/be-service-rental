package com.gea.app.rental.dto;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Request payload for updating a rental.
 */
@Getter
public class RentalUpdateRequest {

    private String renterName;
    private String renterPhone;
    private String renterAddress;
    private String renterIdNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String pickupLocation;
    private String returnLocation;

    @Min(0)
    private Long priceTotal;

    private String notes;
}
