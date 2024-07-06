package com.example.hamro_barber.controller;

import com.example.hamro_barber.mapper.BarberMapper;
import com.example.hamro_barber.mapper.FavouriteMapper;
import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.service.serviceImpl.CustomerServiceImpl;
import com.example.hamro_barber.service.serviceImpl.FavouriteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/favourite")
@AllArgsConstructor
public class FavouriteController {
    private FavouriteServiceImpl favouriteService;
    private CustomerServiceImpl customerService;
    private FavouriteMapper favouriteMapper;

    @GetMapping("/get/customer/{customerId}")
    public ResponseEntity<?> getFavouritesOfCustomer(@PathVariable("customerId") Integer customerId) {
        customerService.findCustomerById(customerId);
        return new ResponseEntity<>(favouriteMapper.favouriteToDto(favouriteService.findFavouritesOfCustomer(customerId)), HttpStatus.OK);
    }

    @PostMapping("/barber/add/{barberId}")
    public ResponseEntity<?> addBarberToFavourite(@PathVariable("barberId") Integer barberId, Principal principal) {
        Customer customer = customerService.findCustomerByEmail(principal.getName());
        return new ResponseEntity<>(favouriteMapper.favouriteToDto(favouriteService.addFavouriteBarber(barberId, customer)), HttpStatus.CREATED);
    }

    @PostMapping("/service/add/{serviceId}")
    public ResponseEntity<?> addServiceToFavourite(@PathVariable("serviceId") Integer serviceId, Principal principal) {
        Customer customer = customerService.findCustomerByEmail(principal.getName());
        return new ResponseEntity<>(favouriteMapper.favouriteToDto(favouriteService.addFavouriteService(serviceId, customer)), HttpStatus.CREATED);
    }

    @PutMapping("/barber/remove/{barberId}")
    public ResponseEntity<?> removeBarberFromFavourite(@PathVariable("barberId") Integer barberId, Principal principal) {
        Customer customer = customerService.findCustomerByEmail(principal.getName());
        return new ResponseEntity<>(favouriteService.removeFavouriteBarber(barberId, customer), HttpStatus.OK);
    }

    @PutMapping("/service/remove/{serviceId}")
    public ResponseEntity<?> removeServiceFromFavourite(@PathVariable("serviceId") Integer serviceId, Principal principal) {
        Customer customer = customerService.findCustomerByEmail(principal.getName());
        return new ResponseEntity<>(favouriteService.removeFavouriteService(serviceId, customer), HttpStatus.OK);
    }

}
