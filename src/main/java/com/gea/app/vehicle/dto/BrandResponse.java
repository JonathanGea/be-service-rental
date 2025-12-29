package com.gea.app.vehicle.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for brand endpoints.
 */
@Getter
@AllArgsConstructor
public class BrandResponse {
    private UUID id;
    private String name;
}
