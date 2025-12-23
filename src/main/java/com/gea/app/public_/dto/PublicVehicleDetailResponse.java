package com.gea.app.public_.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Vehicle detail for landing page.
 */
@Getter
@AllArgsConstructor
public class PublicVehicleDetailResponse {
    private UUID id;
    private String name;
    private String brand;
    private String type;
    private Integer year;
    private String transmission;
    private Integer capacity;
    private Long pricePerDay;
    private String status;
    private List<LocalDate> availableDates;
    private List<String> images;
    private String whatsAppLink;
}
