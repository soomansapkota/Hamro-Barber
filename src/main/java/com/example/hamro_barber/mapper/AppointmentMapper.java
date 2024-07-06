package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.Appointment;
import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.model.dto.AppointmentDto;
import com.example.hamro_barber.model.dto.BarberDto;
import com.example.hamro_barber.model.dto.ServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "services", qualifiedByName = "noBarber")
    @Mapping(target = "barber", qualifiedByName = "noService")
    AppointmentDto appointmentToDto(Appointment appointment);
    Appointment dtoToAppointment(AppointmentDto appointmentDto);
    List<AppointmentDto> listAppointmentToDto(List<Appointment> appointments);
    List<Appointment> listDtoToAppointment(List<AppointmentDto> appointmentDtos);

    @Named("noBarber")
    @Mapping(target = "barber", ignore = true)
    ServiceDto toServiceDto(Services services);
    List<ServiceDto> toListServiceDto(List<Services> services);

    @Named("noService")
    @Mapping(target = "services", ignore = true)
    BarberDto barberToDto(Barber barber);
    List<BarberDto> listBarberToDto(List<Barber> barbers);
}
