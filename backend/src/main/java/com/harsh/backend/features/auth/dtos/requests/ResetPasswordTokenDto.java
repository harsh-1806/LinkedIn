package com.harsh.backend.features.auth.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordTokenDto {
    private String email;
    private String token;
    private String newPassword;
}
