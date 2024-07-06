package com.example.hamro_barber.service;

import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.helper.ApiResponse;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer findCustomerById(Integer customerId);
    Customer findCustomerByEmail(String email);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    ApiResponse deleteCustomer(Integer customerId);
}
