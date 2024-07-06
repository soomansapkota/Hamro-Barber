package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.User;
import com.example.hamro_barber.model.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(User user);
    User dtoToUser(UserDto userDto);
    List<UserDto> listUserToDto(List<User> users);
    List<User> listDtoToUser(List<UserDto> userDtos);
}
