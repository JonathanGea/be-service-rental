package com.gea.app.auth;

import com.gea.app.auth.dto.AuthResponse;
import com.gea.app.auth.dto.LoginRequest;
import com.gea.app.auth.dto.RegisterRequest;
import com.gea.app.shared.exception.ApiError;
import com.gea.app.shared.exception.ApiException;
import com.gea.app.shared.util.JwtService;
import com.gea.app.user.enum_.Role;
import com.gea.app.user.entity.User;
import com.gea.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, ApiError.builder()
                    .code("USER_ALREADY_EXISTS")
                    .message("Email '" + req.getEmail() + "' sudah terdaftar.")
                    .build());
        }
        var user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole().name()));
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, ApiError.builder()
                    .code("UNAUTHORIZED_ACCESS")
                    .message("Email atau password salah.")
                    .build());
        }

        var user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, ApiError.builder()
                        .code("UNAUTHORIZED_ACCESS")
                        .message("Email atau password salah.")
                        .build()));
        String token = jwtService.generateToken(user.getUsername(), Map.of("role", user.getRole().name()));
        return new AuthResponse(token);
    }
}
