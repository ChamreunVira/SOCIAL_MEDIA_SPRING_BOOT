package com.kh.chamreunvira.springboot.security;

import com.kh.chamreunvira.springboot.dto.AuthRequest;
import com.kh.chamreunvira.springboot.dto.UserRequest;
import com.kh.chamreunvira.springboot.dto.UserResponse;
import com.kh.chamreunvira.springboot.model.User;

public interface AuthService {

    UserResponse register(UserRequest request);
    UserResponse login(AuthRequest request);
    User fetchUserById(Long id);
    User fetchUserByEmail(String email);

}
