package com.example.hamro_barber.mapper;

import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.model.dto.CustomerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDto customerDto);
    List<CustomerDto> listCustomerToDto(List<Customer> customers);
    List<Customer> listDtoToCustomer(List<CustomerDto> customerDtos);
}
