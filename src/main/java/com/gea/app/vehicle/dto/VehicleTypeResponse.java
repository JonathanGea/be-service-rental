package com.gea.app.vehicle.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for vehicle type endpoints.
 */
@Getter
@AllArgsConstructor
public class VehicleTypeResponse {
    private UUID id;
    private String name;
}
