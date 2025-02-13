package com.harsh.backend.features.auth.services.impl;

import com.harsh.backend.features.auth.dtos.requests.LoginRequestDto;
import com.harsh.backend.features.auth.dtos.requests.SignupRequestDto;
import com.harsh.backend.features.auth.dtos.responses.JwtResponse;
import com.harsh.backend.features.auth.exceptions.EmailAlreadyExistsException;
import com.harsh.backend.features.auth.exceptions.UserNotFoundException;
import com.harsh.backend.features.auth.model.User;
import com.harsh.backend.features.auth.repository.UserRepository;
import com.harsh.backend.features.auth.services.AuthService;
import com.harsh.backend.features.auth.utils.EmailUtil;
import com.harsh.backend.features.auth.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final EmailUtil emailUtil;

    private final long EXPIRE_DURATION_MINUTES = 15;

    @Autowired
    public AuthServiceImpl(UserRepository authenticatedUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, EmailUtil emailUtil) {
        this.userRepository = authenticatedUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;
    }


    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not found with email : " + email));
    }

    @Override
    @Transactional
    public JwtResponse register(SignupRequestDto signupRequestDto) {

        // Check if the user exists in the DB

        Optional<User> userOptional = userRepository.findByEmail(signupRequestDto.getEmail());

        if(userOptional.isPresent() && userOptional.get().getIsVerified()) {
            throw new EmailAlreadyExistsException("User Exists with emailId + " + signupRequestDto.getEmail() + " already exits and is verified.");
        }

        // TODO: Use Redis to temporarily store the emailToken & userInfo
        // Send email verification token

        User savedUser = sendEmailVerificationToken(signupRequestDto);

        // Save the user and keep isVerified field false.

        String jwtToken = jwtUtil.generateToken(savedUser.getEmail(), List.of("USER"));
        return new JwtResponse(jwtToken, savedUser.getUserId().toString());
    }

    private User sendEmailVerificationToken(SignupRequestDto signupRequestDto) {
        String token = emailUtil.generateVerificationCode();

        User user = User
                .builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .isVerified(false)
                .emailVerificationToken(passwordEncoder.encode(token))
                .emailVerificationTokenExpiry(LocalDateTime.now().plusMinutes(EXPIRE_DURATION_MINUTES))
                .build();

        User savedUser = userRepository.save(user);

        emailUtil.sendVerificationEmail(signupRequestDto.getEmail(), token, EXPIRE_DURATION_MINUTES);

        return savedUser;
    }

    @Override
    public void validateEmail(String token, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email verification failed.");
        }

        User user = userOptional.get();

        if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Email verification token expired.");
        }

        if (!passwordEncoder.matches(token, user.getEmailVerificationToken())) {
            throw new IllegalArgumentException("Wrong email verification token.");
        }

        // Mark email as verified and clear the token
        user.setIsVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        userRepository.save(user);
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
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(signupRequestDto.getEmail(), List.of("ADMIN", "USER"));
        return new JwtResponse(jwtToken, savedUser.getUserId().toString());
    }

    @Override
    @Transactional
    public void sendPasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            String passwordResetToken = emailUtil.generateVerificationCode();
            String hashedToken = passwordEncoder.encode(passwordResetToken);

            emailUtil.sendPasswordResetToken(email, passwordResetToken, EXPIRE_DURATION_MINUTES);

            User user = userOptional.get();
            user.setPasswordResetToken(hashedToken);
            user.setPasswordResetTokenExpiry(LocalDateTime.now().plusMinutes(EXPIRE_DURATION_MINUTES));
            userRepository.save(user);

        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    @Override
    public void resetPassword(String email, String newPassword, String token) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()) {
            User user = userOptional.get();

            if(passwordEncoder.matches(token, user.getPasswordResetToken()) && user.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now())) {
                user.setPasswordResetToken(null);
                user.setPasswordResetTokenExpiry(null);
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            }
            else if(passwordEncoder.matches(token, user.getPasswordResetToken()) && !user.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Password reset token expired!");
            }
            else {
                throw new IllegalStateException("Password reset token invalid!");
            }

        }
    }
}
