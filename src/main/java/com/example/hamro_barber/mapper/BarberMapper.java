package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.model.dto.BarberDto;
import com.example.hamro_barber.model.dto.ServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BarberMapper {
    @Mapping(target = "services", qualifiedByName = "noBarber")
    BarberDto barberToDto(Barber barber);
    Barber dtoToBarber(BarberDto barberDto);
    List<BarberDto> listBarberToDto(List<Barber> barbers);
    List<Barber> listDtoToBarber(List<BarberDto> barberDtos);

    @Named("noBarber")
    @Mapping(target = "barber", ignore = true)
    ServiceDto toServiceDto(Services services);
    List<ServiceDto> toListServiceDto(List<Services> services);
}
