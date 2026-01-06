package com.kh.chamreunvira.springboot.mapper;

import com.kh.chamreunvira.springboot.dto.UserRequest;
import com.kh.chamreunvira.springboot.dto.UserResponse;
import com.kh.chamreunvira.springboot.enumz.Role;
import com.kh.chamreunvira.springboot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public UserResponse mapperToResponse(User user , String token) {
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getFullName())
                .email(user.getUsername())
                .token(token)
                .role(user.getRoles().stream().map(Enum::name).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User mapperToEntity(UserRequest req) {
        return User
                .builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(List.of(Role.USER))
                .build();
    }

}
