package com.example.hamro_barber.model.dto;

import com.example.hamro_barber.model.AppointmentStatus;
import com.example.hamro_barber.model.Services;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private Integer id;
    private Long bookingStart;
    private Long bookingEnd;
    private BarberDto barber;
    private CustomerDto customer;
    private AppointmentStatus status;
    private List<ServiceDto> services;
}
