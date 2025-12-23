package com.gea.app.shared.exception;

import com.gea.app.shared.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
        ApiResponse<Object> response = new ApiResponse<>(false, List.of(ex.getError()));
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        ApiError error = ApiError.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("Terjadi kesalahan internal yang tidak terduga. Silakan coba lagi nanti atau hubungi administrator.")
                .details(Map.of("suggestion", "Please provide the traceId to support staff for faster assistance."))
                .build();
        ApiResponse<Object> response = new ApiResponse<>(false, List.of(error));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ApiError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapValidationError)
                .collect(Collectors.toList());

        ApiResponse<List<ApiError>> response = new ApiResponse<>(false, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ApiError mapValidationError(FieldError fieldError) {
        String field = fieldError.getField();
        String code = fieldError.getCode();
        if ("NotBlank".equals(code) || "NotNull".equals(code) || "NotEmpty".equals(code)) {
            return ApiError.builder()
                    .code("VALIDATION_BLANK")
                    .message("Kolom '" + field + "' tidak boleh kosong.")
                    .field(field)
                    .build();
        }
        if ("Email".equals(code)) {
            return ApiError.builder()
                    .code("VALIDATION_INVALID_EMAIL")
                    .message("Format email tidak valid.")
                    .field(field)
                    .details(Map.of("expectedFormat", "email"))
                    .build();
        }
        if ("Size".equals(code)) {
            Integer min = extractMinSize(fieldError);
            return ApiError.builder()
                    .code("VALIDATION_MIN_LENGTH")
                    .message(min != null
                            ? "Kolom '" + field + "' minimal " + min + " karakter."
                            : "Panjang kolom '" + field + "' tidak valid.")
                    .field(field)
                    .details(min != null ? Map.of("minLength", min) : null)
                    .build();
        }
        return ApiError.builder()
                .code("VALIDATION_INVALID")
                .message("Validasi gagal pada kolom '" + field + "'.")
                .field(field)
                .build();
    }

    private Integer extractMinSize(FieldError fieldError) {
        if (fieldError.getArguments() == null) {
            return null;
        }
        for (Object arg : fieldError.getArguments()) {
            if (arg instanceof Integer) {
                return (Integer) arg;
            }
            if (arg instanceof Long) {
                return ((Long) arg).intValue();
            }
        }
        return null;
    }
}
