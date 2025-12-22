package com.gea.app.shared.exception;

import com.gea.app.shared.model.dto.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiResponse<Object> response = new ApiResponse<>(false, errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiResponse<List<ValidationError>> response = new ApiResponse<>(false, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @Getter
    private static class ValidationError {
        private final String field;
        private final String issue;

        public ValidationError(String field, String issue) {
            this.field = field;
            this.issue = issue;
        }

    }
}
