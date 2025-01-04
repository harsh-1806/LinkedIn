package com.harsh.backend.features.auth.controller;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.services.AuthService;
import com.harsh.backend.features.auth.services.impl.UserDetailsServiceImpl;
import com.harsh.backend.features.auth.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(
            @RequestBody @Valid
            LoginRequestDto loginRequestDto
    ) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(
            @RequestBody @Valid
            SignupRequestDto signupRequestDto
    ) {
        return ResponseEntity.ok(authService.register(signupRequestDto));
    }
    @PostMapping("/register-admin")
    public ResponseEntity<JwtResponse> registerAdmin(
            @RequestBody @Valid
            SignupRequestDto signupRequestDto
    ) {
        return ResponseEntity.ok(authService.registerAdmin(signupRequestDto));
    }


}
