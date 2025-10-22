package com.harsh.authservice.services;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMail(String email, String subject, String content) throws MessagingException, UnsupportedEncodingException;
}
