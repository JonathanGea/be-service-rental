package com.gea.app.public_.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response wrapper for public vehicle list.
 */
@Getter
@AllArgsConstructor
public class PublicVehiclesResponse {
    private List<PublicVehicleItemResponse> items;
}
