package com.example.hamro_barber.service;

import com.example.hamro_barber.model.Appointment;
import com.example.hamro_barber.model.dto.AppointmentRegisterDto;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.model.dto.BarberAppointmentDto;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(AppointmentRegisterDto appointment);
    List<Appointment> getAllAppointments();
    Appointment getAppointment(Integer appointmentId);
    List<Appointment> getAppointmentsOfBarber(Integer barberId);
    List<Appointment> getAppointmentsOfCustomer(Integer customerId, String status);
    Appointment updateAppointment(Appointment appointment);
    ApiResponse deleteAppointment(Integer appointmentId);
    boolean checkBarberAvailability(Integer barberId, Long bookingStart, Long bookingEnd);
    boolean checkCustomerAvailability(Integer customerId, Long bookingStart, Long bookingEnd);

    List<BarberAppointmentDto> getUpcommingAppointmentsOfBarber(Integer barberId, String status);
}
