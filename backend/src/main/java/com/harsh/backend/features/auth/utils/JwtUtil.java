package com.harsh.backend.features.auth.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface JwtUtil {
    String extractEmail(String token);

    Date extractExpiration(String token);

    List<String> extractRoles(String token);

    boolean isTokenExpired(String token);

    String generateToken(String email, List<String> roles);

    String generateToken(Map<String, Object> claims, String email);

    boolean validateToken(String token, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    Claims extractAllClaims(String token);

    Key getSignKey();

}
