package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavouriteDto {
    private Integer id;
    private List<BarberDto> barbers;
    private List<ServiceDto> services;
    private CustomerDto customer;
}
