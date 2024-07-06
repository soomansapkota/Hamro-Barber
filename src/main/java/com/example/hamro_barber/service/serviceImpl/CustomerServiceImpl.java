package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.exception.ResourceNotFoundException;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.repository.CustomerRepository;
import com.example.hamro_barber.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserServiceImpl userService;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return customerRepository.findByUserId(user.getId()).orElseThrow(() -> new  CustomException("User not found"));
    }


    @Override
    public Customer createCustomer(Customer customer) {
        User user = userService.findUserByEmail(customer.getUser().getEmail());
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        User user = userService.findUserByEmail(customer.getUser().getEmail());
        Customer existingCustomer = findCustomerById(customer.getId());
        User updatedUser = userService.updateUser(customer.getUser());
        existingCustomer.setUser(updatedUser);
        return customerRepository.save(existingCustomer);
    }

    @Override
    public ApiResponse deleteCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return new ApiResponse(true, "User successfully deleted");
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}
