package com.gea.app.shared.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private boolean isSuccess;
    private List<?> errors;
    private T data;
    private Date timestamp;

    public ApiResponse() {
        ZoneId jakartaZone = ZoneId.of("Asia/Jakarta");
        ZonedDateTime jakartaTime = ZonedDateTime.now(jakartaZone);
        this.timestamp = Date.from(jakartaTime.toInstant());
    }

    public ApiResponse(boolean isSuccess, T data, List<?> errors) {
        this();
        this.isSuccess = isSuccess;
        this.data = data;
        this.errors = errors;
    }

    public ApiResponse(boolean isSuccess, T data) {
        this(isSuccess, data, null);
    }

    public ApiResponse(boolean isSuccess, List<?> errors) {
        this(isSuccess, null, errors);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> failure(List<?> errors) {
        return new ApiResponse<>(false, null, errors);
    }
}
