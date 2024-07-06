package com.example.hamro_barber.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SocketDto {
    private Long id;
    private String name;
    private String service;
}
