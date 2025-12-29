package com.gea.app.auth;

import com.gea.app.auth.dto.AuthResponse;
import com.gea.app.auth.dto.LoginRequest;
import com.gea.app.auth.dto.RegisterRequest;
import com.gea.app.auth.AuthService;
import com.gea.app.shared.model.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        var data = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        var data = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
