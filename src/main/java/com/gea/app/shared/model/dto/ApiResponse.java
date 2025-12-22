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

    public ApiResponse(boolean isSuccess, T data) {
        this();
        this.isSuccess = isSuccess;
        this.data = data;
        this.errors = null;
    }

    public ApiResponse(boolean isSuccess, List<?> errors) {
        this();
        this.isSuccess = isSuccess;
        this.errors = errors;
        this.data = null;
    }
}
