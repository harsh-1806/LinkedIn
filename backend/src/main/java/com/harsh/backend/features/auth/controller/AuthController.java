package com.harsh.backend.features.auth.controller;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.ResetPasswordRequestDto;
import com.harsh.backend.features.auth.dtos.requests.ResetPasswordTokenDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.ApiResponse;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.model.User;
import com.harsh.backend.features.auth.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
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
    ) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(authService.register(signupRequestDto));
    }


    @GetMapping("/send-verification-email")
    public ResponseEntity<ApiResponse> sendVerificationEmail(
            @RequestAttribute("attributedUser")
            User user
    ) {
       authService.sendPasswordResetToken(user.getEmail());

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Verification Email sent successfully!")
                .status(HttpStatus.ACCEPTED)
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }



    @PutMapping("/validate-email")
    public ResponseEntity<ApiResponse> verifyEmail(
            @RequestParam
            String token,
            @RequestAttribute("authenticatedUser")
            User user
    ) {
        authService.validateEmail(token, user.getEmail());

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Email Verified Successfully!")
                        .status(HttpStatus.OK)
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody
            ResetPasswordRequestDto resetPasswordRequestDto
    ) {
        authService.sendPasswordResetToken(resetPasswordRequestDto.getEmail());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Password Reset Token sent to :" + resetPasswordRequestDto.getEmail())
                        .status(HttpStatus.OK)
                        .success(true)
                        .build()
        );
    }

    // TODO: validate-reset-password endpoint not working properly ? backend / frontend

    @PostMapping("/validate-reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody
            ResetPasswordTokenDto resetPasswordTokenDto
    ) {
        authService.resetPassword(resetPasswordTokenDto.getEmail(), resetPasswordTokenDto.getNewPassword(), resetPasswordTokenDto.getToken());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Password Reset for " + resetPasswordTokenDto.getEmail() + " successful.")
                        .status(HttpStatus.OK)
                        .success(true)
                        .build()
        );
    }




    @PostMapping("/register-admin")
    public ResponseEntity<JwtResponse> registerAdmin(
            @RequestBody @Valid
            SignupRequestDto signupRequestDto
    ) {
        return ResponseEntity.ok(authService.registerAdmin(signupRequestDto));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestAttribute("authenticatedUser") User user) {
        return ResponseEntity.ok(user);
    }


}
