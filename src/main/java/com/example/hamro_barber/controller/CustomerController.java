package com.example.hamro_barber.controller;

import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.mapper.CustomerMapper;
import com.example.hamro_barber.service.serviceImpl.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(customerMapper.listCustomerToDto(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer customerId) {
        return new ResponseEntity<>(customerMapper.customerToDto(customerService.findCustomerById(customerId)), HttpStatus.OK);
    }

    @GetMapping("/get-email/{email}")
    public ResponseEntity<?> getCustomer(@PathVariable String email) {
        return new ResponseEntity<>(customerMapper.customerToDto(customerService.findCustomerByEmail(email)), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerMapper.customerToDto(customerService.createCustomer(customer)), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerMapper.customerToDto(customerService.updateCustomer(customer)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deletedCustomer(@PathVariable Integer customerId) {
        return new ResponseEntity<>(customerService.deleteCustomer(customerId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-logged-in-user")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        return new ResponseEntity<>(customerMapper.customerToDto(customerService.findCustomerByEmail(principal.getName())), HttpStatus.OK);
    }
}
