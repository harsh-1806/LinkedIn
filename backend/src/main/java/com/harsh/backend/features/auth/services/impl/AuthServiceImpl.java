package com.harsh.backend.features.auth.services.impl;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.exceptions.EmailAlreadyExistsException;
import com.harsh.backend.features.auth.exceptions.UserNotFoundException;
import com.harsh.backend.features.auth.model.User;
import com.harsh.backend.features.auth.repository.UserRepository;
import com.harsh.backend.features.auth.services.AuthService;
import com.harsh.backend.features.auth.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository authenticatedUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = authenticatedUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not found with email : " + email));
    }

    @Override
    @Transactional
    public JwtResponse register(SignupRequestDto signupRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequestDto.getEmail());

        if(userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("User Exists with emailId + " + signupRequestDto.getEmail());
        }

        User user = User
                .builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .firstName(signupRequestDto.getFirstName())
                .lastName(signupRequestDto.getLastName())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(signupRequestDto.getEmail(), List.of("USER"));
        return new JwtResponse(jwtToken, savedUser.getUserId().toString());
    }

    @Override
    public JwtResponse login(LoginRequestDto loginRequestDto) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequestDto.getEmail());

            if(userOptional.isPresent()) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());

                User user = userOptional.get();

                String jwtToken = jwtUtil.generateToken(userDetails.getUsername(), List.of("USER"));

                return new JwtResponse(jwtToken, user.getUserId().toString());
            }
            else {
                throw new UserNotFoundException("User not found with emailId : " + loginRequestDto.getEmail());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JwtResponse registerAdmin(SignupRequestDto signupRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequestDto.getEmail());

        if(userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("User Exists with emailId + " + signupRequestDto.getEmail());
        }

        User user = User
                .builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .firstName(signupRequestDto.getFirstName())
                .lastName(signupRequestDto.getLastName())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(signupRequestDto.getEmail(), List.of("ADMIN", "USER"));
        return new JwtResponse(jwtToken, savedUser.getUserId().toString());
    }
}
