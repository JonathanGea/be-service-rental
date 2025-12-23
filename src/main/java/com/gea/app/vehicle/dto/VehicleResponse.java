package com.gea.app.vehicle.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for vehicle endpoints.
 */
@Getter
@AllArgsConstructor
public class VehicleResponse {
    private UUID id;
    private String name;
    private String brand;
    private String type;
    private Integer year;
    private String transmission;
    private Integer capacity;
    private Long pricePerDay;
    private String description;
    private String status;
    private UUID categoryId;
}
