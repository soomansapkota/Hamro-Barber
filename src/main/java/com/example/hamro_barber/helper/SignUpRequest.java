package com.example.hamro_barber.helper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
//    @NotBlank
//    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;

    private String phone;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private String panNo;
}