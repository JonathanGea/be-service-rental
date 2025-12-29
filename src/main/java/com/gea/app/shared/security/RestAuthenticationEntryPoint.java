package com.gea.app.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.model.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var body = ApiResponse.failure(List.of(ApiError.builder()
                .code("UNAUTHORIZED_ACCESS")
                .message("Anda tidak memiliki token otentikasi yang valid atau token telah kadaluarsa.")
                .build()));

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
