package com.gea.app.category.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response payload for category.
 */
@Getter
@AllArgsConstructor
public class CategoryResponse {
    private UUID id;
    private String name;
}
