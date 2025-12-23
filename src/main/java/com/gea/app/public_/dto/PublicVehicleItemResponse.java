package com.gea.app.public_.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Vehicle summary for landing page list.
 */
@Getter
@AllArgsConstructor
public class PublicVehicleItemResponse {
    private UUID id;
    private String name;
    private Long pricePerDay;
    private String status;
    private LocalDate nextAvailableDate;
    private String thumbnailUrl;
}
