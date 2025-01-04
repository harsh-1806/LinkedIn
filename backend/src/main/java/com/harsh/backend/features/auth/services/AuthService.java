package com.harsh.backend.features.auth.services;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.model.User;


public interface AuthService {


    User getUser(String email);

    JwtResponse register(SignupRequestDto signupRequestDto);

    JwtResponse login(LoginRequestDto loginRequestDto);

    JwtResponse registerAdmin(SignupRequestDto signupRequestDto);
}
