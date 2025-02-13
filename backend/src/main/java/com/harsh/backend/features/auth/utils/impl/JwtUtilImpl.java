package com.harsh.backend.features.auth.utils.impl;

import com.harsh.backend.features.auth.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtilImpl implements JwtUtil {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.expirationInMinutes}")
    private Long jwtExpiration;

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return (Claims) Jwts
                .parser()
                .verifyWith(getSignKey())
                .decryptWith(getSignKey())
                .build()
                .parse(token)
                .getPayload();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    @Override
    public String generateToken(String email, List<String> role) {
        Map<String, Object> claims = Map.of("roles", role);
        return generateToken(claims, email);
    }

    @Override
    public String generateToken(Map<String, Object> claims, String email) {
        return Jwts
                .builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (60 * 1000L * jwtExpiration)))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).toInstant().isBefore(Instant.now());
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
