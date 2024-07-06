package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.helper.MapCaseManipulation;
import com.example.hamro_barber.model.*;
import com.example.hamro_barber.model.dto.AppointmentRegisterDto;
import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.exception.ResourceNotFoundException;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.model.dto.BarberAppointmentDto;
import com.example.hamro_barber.repository.AppointmentRepository;
import com.example.hamro_barber.service.AppointmentService;
import com.example.hamro_barber.service.BarberService;
import com.example.hamro_barber.service.CustomerService;
import com.example.hamro_barber.service.ServicesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final BarberService barberService;
    private final CustomerService customerService;
    private final ServicesService servicesService;
    @Override
    public Appointment createAppointment(AppointmentRegisterDto registerDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Barber barber = barberService.findBarberById(registerDto.getBarberId());
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        boolean isBarberAvailable = checkBarberAvailability(registerDto.getBarberId(), registerDto.getBookingStart(), registerDto.getBookingEnd());
        System.out.println("Is barber available = " + isBarberAvailable);
        if (!isBarberAvailable) {
            throw new CustomException("Barber not available");
        }
        boolean isCustomerAvailable = checkCustomerAvailability(customer.getId(), registerDto.getBookingStart(), registerDto.getBookingEnd());
        if (!isCustomerAvailable) {
            throw new CustomException("You have another appointment at this time");
        }
        List<Services> servicesList = new ArrayList<>();
        for (Integer serviceId : registerDto.getServicesIds()) {
            Services service = servicesService.getService(serviceId);
            servicesList.add(service);
        }
        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setBarber(barber);
        appointment.setBookingEnd(registerDto.getBookingEnd());
        appointment.setBookingStart(registerDto.getBookingStart());
        appointment.setServices(servicesList);
        appointment.setStatus(AppointmentStatus.UPCOMING);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointment(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            return appointment.get();
        } else {
            throw new ResourceNotFoundException("Appointment not available");
        }
    }

    @Override
    public List<Appointment> getAppointmentsOfBarber(Integer barberId) {
        barberService.findBarberById(barberId);
        List<Appointment> appointmentsByBarberId = appointmentRepository.findAppointmentsByBarber_Id(barberId);
        return appointmentsByBarberId;
    }

    @Override
    public List<Appointment> getAppointmentsOfCustomer(Integer customerId, String status) {
        customerService.findCustomerById(customerId);
        if (status == null) {
            return appointmentRepository.findAppointmentsByCustomer_Id((customerId));
        } else {
            return appointmentRepository.findAppointmentOfCustomerByStatus(customerId, status);
        }
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        barberService.findBarberById(appointment.getBarber().getId());
        customerService.findCustomerById(appointment.getCustomer().getId());
        Appointment existingAppointment = getAppointment(appointment.getId());
        existingAppointment.setBarber(appointment.getBarber());
        existingAppointment.setCustomer(appointment.getCustomer());
        existingAppointment.setBookingStart(appointment.getBookingStart());
        existingAppointment.setBookingEnd(appointment.getBookingEnd());
        existingAppointment = appointmentRepository.save(existingAppointment);
        return existingAppointment;
    }

    @Override
    public ApiResponse deleteAppointment(Integer appointmentId) {
        Appointment appointment = getAppointment(appointmentId);
        appointmentRepository.delete(appointment);
        return new ApiResponse(true, "Appointment cancelled");
    }

    @Override
    public boolean checkBarberAvailability(Integer barberId, Long bookingStart, Long bookingEnd) {
        barberService.findBarberById(barberId);
        Long serviceTime = bookingEnd - bookingStart;
        Long reserveTime = bookingStart - serviceTime;
        if (bookingStart < System.currentTimeMillis()/1000 || bookingEnd < bookingStart) {
            throw new CustomException("You cannot choose previous date");
        }
        Optional<Appointment> appointment = appointmentRepository.checkBarberAvailability(barberId, bookingStart, bookingEnd, reserveTime);
        return appointment.isEmpty();
    }

    @Override
    public boolean checkCustomerAvailability(Integer customerId, Long bookingStart, Long bookingEnd) {
        customerService.findCustomerById(customerId);
        Optional<Appointment> appointment = appointmentRepository.checkCustomerAvailability(customerId, bookingStart, bookingEnd);
        return appointment.isEmpty();
    }

    @Override
    public List<BarberAppointmentDto> getUpcommingAppointmentsOfBarber(Integer barberId, String status) {
        List<Map<String, Object>> results = appointmentRepository.getAppointmentOfBarber(barberId, status);
        MapCaseManipulation mapCaseManipulation = new MapCaseManipulation();
        results = mapCaseManipulation.keyToCamelCase(results);
        ObjectMapper objectMapper = new ObjectMapper();
        return results.stream().map(map -> objectMapper.convertValue(map, BarberAppointmentDto.class)).collect(Collectors.toList());
    }
}
