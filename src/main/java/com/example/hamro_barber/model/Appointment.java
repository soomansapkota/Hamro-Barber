package com.example.hamro_barber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long bookingStart;
    private Long bookingEnd;
    @ManyToOne
    private Barber barber;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Services> services;
}
