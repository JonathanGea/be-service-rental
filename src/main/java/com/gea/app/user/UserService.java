package com.gea.app.user;

import com.gea.app.user.entity.User;
import com.gea.app.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }


    public UserResponse getMe(User principal) {
        return toDto(principal);
    }

    private UserResponse toDto(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getRole().name());
    }
}
