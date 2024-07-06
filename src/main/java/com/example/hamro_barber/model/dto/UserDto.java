package com.example.hamro_barber.model.dto;

import com.example.hamro_barber.helper.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private String imageUrl;
}
