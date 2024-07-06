package com.example.hamro_barber.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeDto {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String otp;
}
