package com.example.hamro_barber.controller;

import com.example.hamro_barber.model.dto.AppointmentRegisterDto;
import com.example.hamro_barber.mapper.AppointmentMapper;
import com.example.hamro_barber.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(AppointmentService appointmentService, AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllAppointment() {
        return new ResponseEntity<>(appointmentMapper.listAppointmentToDto(appointmentService.getAllAppointments()), HttpStatus.OK);
    }

    @GetMapping("/get/{appointmentId}")
    public ResponseEntity<?> getAppointment(@PathVariable Integer appointmentId) {
        return new ResponseEntity<>(appointmentMapper.appointmentToDto(appointmentService.getAppointment(appointmentId)), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRegisterDto appointment) {
        return new ResponseEntity<>(appointmentMapper.appointmentToDto(appointmentService.createAppointment(appointment)), HttpStatus.OK);
    }

//    @GetMapping("/get/barber/{barberId}")
//    public ResponseEntity<?> getBarberAppointment(@PathVariable Integer barberId) {
//        return new ResponseEntity<>(appointmentMapper.listAppointmentToDto(appointmentService.getAppointmentsOfBarber(barberId)), HttpStatus.OK);
//    }

    @GetMapping("/get/barber/{barberId}")
    public ResponseEntity<?> getBarberAppointment(@PathVariable Integer barberId, @RequestParam String status) {
        return new ResponseEntity<>(appointmentService.getUpcommingAppointmentsOfBarber(barberId, status), HttpStatus.OK);
    }

    @GetMapping("/get/customer/{customerId}")
    public ResponseEntity<?> getCustomerAppointment(
            @PathVariable("customerId") Integer customerId,
            @RequestParam(required = false, name = "status") String status) {
        return new ResponseEntity<>(appointmentMapper.listAppointmentToDto(appointmentService.getAppointmentsOfCustomer(customerId, status)), HttpStatus.OK);
    }

//    @GetMapping("/get/upcoming")
//    public ResponseEntity<?> getAppointmentUpcoming() {
//        return new ResponseEntity<>(appointmentMapper.listAppointmentToDto(appointmentService.getAppointmentsOfCustomer()))
//    }
}
