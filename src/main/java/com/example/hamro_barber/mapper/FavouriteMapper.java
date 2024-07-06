package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Favourite;
import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.model.dto.BarberDto;
import com.example.hamro_barber.model.dto.FavouriteDto;
import com.example.hamro_barber.model.dto.ServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavouriteMapper {
    @Mapping(target = "services", qualifiedByName = "noBarber")
    @Mapping(target = "barbers", qualifiedByName = "noServices")
    FavouriteDto favouriteToDto(Favourite favourite);
    Favourite dtoToFavourite(FavouriteDto favouriteDto);
    List<FavouriteDto> listFavouriteToDto(List<Favourite> favourites);
    List<Favourite> listDtoToFavourite(List<FavouriteDto> favouriteDtos);

    @Named("noServices")
    @Mapping(target = "services", ignore = true)
    BarberDto toBarberDto(Barber barber);
    List<BarberDto> toListBarberDto(List<Barber> barbers);

    @Named("noBarber")
    @Mapping(target = "barber", ignore = true)
    ServiceDto toServiceDto(Services services);
    List<ServiceDto> toListServiceDto(List<Services> services);
}
