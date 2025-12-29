package com.gea.app.user;

import com.gea.app.shared.model.dto.ApiResponse;
import com.gea.app.user.dto.UserResponse;
import com.gea.app.user.entity.User;
import com.gea.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users -> list semua user (auth required; bisa tambahkan @PreAuthorize untuk ADMIN-only)
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
        var data = userService.getUsers();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    // GET /api/users/me -> profil dari token JWT
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(@AuthenticationPrincipal User principal) {
        var data = userService.getMe(principal);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
