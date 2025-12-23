package com.gea.app.shared.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Standard error payload for ApiResponse.
 */
@Getter
@Builder
public class ApiError {
    private final String code;
    private final String message;
    private final String field;
    private final Map<String, Object> details;
}
