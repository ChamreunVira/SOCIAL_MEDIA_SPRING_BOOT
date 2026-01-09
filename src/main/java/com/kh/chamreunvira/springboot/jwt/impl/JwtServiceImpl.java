package com.kh.chamreunvira.springboot.jwt.impl;

import com.kh.chamreunvira.springboot.exception.CustomMessageException;
import com.kh.chamreunvira.springboot.jwt.JwtConfig;
import com.kh.chamreunvira.springboot.jwt.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl extends JwtConfig implements JwtService {

    @Override
    public SecretKey getSignKey() {
        byte[] byteKey = Decoders.BASE64URL.decode(getSecret());
        return Keys.hmacShaKeyFor(byteKey);
    }

    @Override
    public String generateToken(String subject) {
        return generateToken(new HashMap<>() , subject);
    }

    @Override
    public String generateToken(Map<String, ?> claims, String subject) {
        return builderToken(claims , subject , getExpiration());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpiration(token));
    }

    @Override
    public String extractEmail(String toke) {
        return extractClaims(toke, Claims::getSubject);
    }

    private String builderToken(Map<String , ?> claims , String subject , Long expiration) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey() , Jwts.SIG.HS256)
                .compact();
    }

    public Date getExpiration(String token) {
        return extractClaims(token , Claims::getExpiration);
    }

    private boolean isTokenExpiration(String token) {
        return getExpiration(token).before(new Date());
    }

    private <T>T extractClaims(String token , Function<Claims , T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {

          return Jwts.parser()
                  .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

          // note tip ex ExpiredJwtException , UnsupportedJwtException , MalformedJwtException , SignatureException

        } catch (ExpiredJwtException ex) {
            log.info(ex.getLocalizedMessage());
            throw new CustomMessageException("Token is expired." , HttpStatus.BAD_REQUEST.value());
        } catch (UnsupportedJwtException ex) {
            log.info(ex.getLocalizedMessage());
            throw new CustomMessageException("Token is not support" , HttpStatus.BAD_REQUEST.value());
        } catch (JwtException ex) {
            log.info(ex.getLocalizedMessage());
            throw new CustomMessageException("Token is invalid format" , HttpStatus.BAD_REQUEST.value());
        } catch (Exception ex) {
            log.info("Something went wrong.");
            throw new RuntimeException("Something went wrong.");
        }
    }

}
