package com.harsh.backend.features.auth.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtUtil {
    String extractEmail(String token);

    Date extractExpiration(String token);

    boolean isTokenExpired(String token);

    String generateToken(String email);

    String generateToken(Map<String, Object> claims, String email);

    boolean validateToken(String token, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    Claims extractAllClaims(String token);

    Key getSignKey();

}
