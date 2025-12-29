package com.gea.app.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Request payload for creating/updating a brand.
 */
@Getter
public class BrandRequest {

    @NotBlank
    private String name;
}
