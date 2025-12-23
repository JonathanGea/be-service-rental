package com.gea.app.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception carrying an ApiError with its HTTP status.
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final ApiError error;

    public ApiException(HttpStatus status, ApiError error) {
        super(error != null ? error.getMessage() : null);
        this.status = status;
        this.error = error;
    }
}
