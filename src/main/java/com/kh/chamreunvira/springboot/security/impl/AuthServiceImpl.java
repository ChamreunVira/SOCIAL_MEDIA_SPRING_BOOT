package com.kh.chamreunvira.springboot.security.impl;

import com.kh.chamreunvira.springboot.dto.AuthRequest;
import com.kh.chamreunvira.springboot.dto.UserRequest;
import com.kh.chamreunvira.springboot.dto.UserResponse;
import com.kh.chamreunvira.springboot.jwt.JwtService;
import com.kh.chamreunvira.springboot.mapper.AuthMapper;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.UserRepository;
import com.kh.chamreunvira.springboot.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(UserRequest request) {
        User user = authMapper.mapperToEntity(request);
        var token = jwtService.generateToken(user.getEmail());
        User saveUser = userRepository.save(user);
        return authMapper.mapperToResponse(saveUser , token);
    }

    @Override
    public UserResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

       User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Email not found with Email: " + request.getEmail()));

       User saveUser = userRepository.save(user);
       var token = jwtService.generateToken(saveUser.getEmail());

        return authMapper.mapperToResponse(saveUser , token);
    }

    @Override
    public User fetchUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return user;
    }

    @Override
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found with email: " + email));
    }
}
