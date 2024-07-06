package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRegisterDto {
    private Integer id;
    private Long bookingStart;
    private Long bookingEnd;
    private Integer barberId;
    private List<Integer> servicesIds;
//    private Integer customerId;
}
