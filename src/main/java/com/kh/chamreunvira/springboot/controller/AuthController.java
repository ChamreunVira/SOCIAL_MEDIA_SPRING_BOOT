package com.kh.chamreunvira.springboot.controller;

import com.kh.chamreunvira.springboot.data.ApiResponse;
import com.kh.chamreunvira.springboot.dto.AuthRequest;
import com.kh.chamreunvira.springboot.dto.UserRequest;
import com.kh.chamreunvira.springboot.dto.UserResponse;
import com.kh.chamreunvira.springboot.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User register successfully." , authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody AuthRequest request) {
        UserResponse response = authService.login(request);
        ResponseCookie cookie = ResponseCookie
                .from("token" , response.getToken())
                .sameSite("Strict")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofSeconds(60 * 10))
                .path("/")
                .build();
        return ResponseEntity.ok().header("Set-Cookie" , cookie.toString()).body(ApiResponse.success("User login successfully." , response));
    }

    @GetMapping("/profile")
    public Map<String , Object> getProfile(@CurrentSecurityContext(expression = "authentication") Object object) {
        return Map.of("name" , object);
    }

}
