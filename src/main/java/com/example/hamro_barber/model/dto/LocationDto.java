package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class LocationDto {
    private BigDecimal longitude;
    private BigDecimal latitude;
}
