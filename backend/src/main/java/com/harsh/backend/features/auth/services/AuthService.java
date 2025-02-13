package com.harsh.backend.features.auth.services;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;


public interface AuthService {


    User getUser(String email);

    JwtResponse register(SignupRequestDto signupRequestDto) throws MessagingException, UnsupportedEncodingException;

    JwtResponse login(LoginRequestDto loginRequestDto);

    JwtResponse registerAdmin(SignupRequestDto signupRequestDto);

    void validateEmail(String token, String email);

    void sendPasswordResetToken(String email);

    void resetPassword(String email, String newPassword, String token);
}
