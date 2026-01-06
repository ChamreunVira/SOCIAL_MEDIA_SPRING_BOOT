package com.kh.chamreunvira.springboot.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface JwtService {

    Key getSignKey();
    String generateToken(String subject);
    String generateToken(Map<String , ?> claims , String subject);
    boolean isTokenValid(String token , UserDetails userDetails);
    String extractEmail(String toke);


}
