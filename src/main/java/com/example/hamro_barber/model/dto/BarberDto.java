package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarberDto {
    private Integer id;

    private String panNo;
    private boolean isOpened;

    private UserDto user;
    private String name;
    private String imageUrl;
    private List<ServiceDto> services;
    private Float rating;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private Double distance;

    public String getName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
