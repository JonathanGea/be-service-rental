package com.gea.app.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Request payload for updating vehicle status.
 */
@Getter
public class VehicleStatusRequest {

    @NotBlank
    private String status;
}
