package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.model.dto.BarberDto;
import com.example.hamro_barber.model.dto.ServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(target = "barber", qualifiedByName = "noBarber")
    ServiceDto serviceToDto(Services service);
    Services dtoToService(ServiceDto serviceDto);
    List<ServiceDto> listServiceToDto(List<Services> services);
    List<Services> listDtoToService(List<ServiceDto> serviceDtos);

    @Named("noBarber")
    @Mapping(target = "services", ignore = true)
    BarberDto toBarberDto(Barber barber);
    List<BarberDto> toListBarberDto(List<Barber> barbers);
}
