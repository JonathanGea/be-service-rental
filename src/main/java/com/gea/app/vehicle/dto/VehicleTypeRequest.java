package com.gea.app.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Request payload for creating/updating a vehicle type.
 */
@Getter
public class VehicleTypeRequest {

    @NotBlank
    private String name;
}
