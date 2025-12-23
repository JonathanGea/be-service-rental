package com.gea.app.rental.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;

/**
 * Request payload for creating a rental.
 */
@Getter
public class RentalRequest {

    @NotNull
    private UUID vehicleId;

    @NotBlank
    private String renterName;

    @NotBlank
    private String renterPhone;

    @NotBlank
    private String renterAddress;

    @NotBlank
    private String renterIdNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String pickupLocation;

    private String returnLocation;

    @NotNull
    @Min(0)
    private Long priceTotal;

    private String notes;
}
