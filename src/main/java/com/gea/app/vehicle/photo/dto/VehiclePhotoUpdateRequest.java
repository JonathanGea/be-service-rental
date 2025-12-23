package com.gea.app.vehicle.photo.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

/**
 * Request payload for updating vehicle photo metadata.
 */
@Getter
public class VehiclePhotoUpdateRequest {

    @Min(1)
    private Integer order;

    private String caption;
}
