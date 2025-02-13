package com.harsh.backend.features.auth.utils;

public interface EmailUtil {
    void sendVerificationEmail(String email, String verificationCode, long expireDuration);
    String generateVerificationCode();
    void sendPasswordResetToken(String email, String verificationCode, long expireDuration);
}
