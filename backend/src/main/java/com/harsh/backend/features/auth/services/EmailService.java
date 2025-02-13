package com.harsh.backend.features.auth.services;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMail(String email, String subject, String content) throws MessagingException, UnsupportedEncodingException;
}
