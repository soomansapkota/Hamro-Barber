package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BarberAppointmentDto {
    private Long id;
    private Long barberId;
    private Long customerId;
    private String   serviceName;
    private String status;
    private Long bookingStart;
    private Long bookingEnd;
}
