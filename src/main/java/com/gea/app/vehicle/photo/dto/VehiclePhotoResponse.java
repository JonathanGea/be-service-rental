package com.gea.app.vehicle.photo.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for vehicle photo endpoints.
 */
@Getter
@AllArgsConstructor
public class VehiclePhotoResponse {
    private UUID id;
    private UUID vehicleId;
    private String url;
    private String caption;
    private Integer order;
}
