package com.gea.app.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.model.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var body = new ApiResponse<>(
                false,
                List.of(ApiError.builder()
                        .code("FORBIDDEN_ACCESS")
                        .message("Anda tidak memiliki izin untuk mengakses resource ini.")
                        .build())
        );

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
