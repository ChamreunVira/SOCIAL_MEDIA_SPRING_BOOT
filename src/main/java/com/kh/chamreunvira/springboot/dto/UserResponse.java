package com.kh.chamreunvira.springboot.dto;

import com.kh.chamreunvira.springboot.enumz.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String token;
    private String profile;
    private String username;
    private String email;
    private List<String> role;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
