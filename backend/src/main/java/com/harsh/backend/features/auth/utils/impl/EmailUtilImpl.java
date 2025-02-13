package com.harsh.backend.features.auth.utils.impl;

import com.harsh.backend.features.auth.services.EmailService;
import com.harsh.backend.features.auth.utils.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Component
public class EmailUtilImpl implements EmailUtil {

    private final EmailService emailService;

    @Autowired
    public EmailUtilImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendVerificationEmail(String email, String token, long expireDuration) {
        String subject = "Verify Your email";
        String body = String.format("""
                Only one step to take full advantage of LinkedIn.
                
                Enter this code to verify your email: %s.
                
                This code will expire in %d minutes.
                """, token, expireDuration);

        try {
            emailService.sendMail(email, subject, body);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(6);

        for(int i = 0; i < 6; ++i) {
            token.append(random.nextInt(10));
        }

        return token.toString();
    }

    @Override
    public void sendPasswordResetToken(String email, String verificationCode, long expireDuration) {
        String subject = "Email Verification";
        String body = String.format("""
                You requested a password reset.
                
                Enter this code to verify your email: %s.
                
                This code will expire in %d minutes.
                """, verificationCode, expireDuration);

        try {
            emailService.sendMail(email, subject, body);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
