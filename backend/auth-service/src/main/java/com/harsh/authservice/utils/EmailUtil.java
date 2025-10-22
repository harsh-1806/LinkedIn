package com.harsh.authservice.utils;

public interface EmailUtil {
    void sendVerificationEmail(String email, String verificationCode, long expireDuration);
    String generateVerificationCode();
    void sendPasswordResetToken(String email, String verificationCode, long expireDuration);
}
