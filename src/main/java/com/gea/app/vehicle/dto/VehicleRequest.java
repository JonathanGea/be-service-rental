package com.gea.app.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

/**
 * Request payload for creating/updating a vehicle.
 */
@Getter
public class VehicleRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String type;

    @NotNull
    @Min(1)
    private Integer year;

    @NotBlank
    private String transmission;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    @Min(0)
    private Long pricePerDay;

    private String description;

    @NotBlank
    private String status;

    private UUID categoryId;
}
