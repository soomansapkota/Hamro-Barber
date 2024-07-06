package com.example.hamro_barber.controller;

import com.example.hamro_barber.email.EmailService;
import com.example.hamro_barber.helper.UserRole;
import com.example.hamro_barber.mapper.BarberMapper;
import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.helper.LoginRequest;
import com.example.hamro_barber.helper.SignUpRequest;
import com.example.hamro_barber.mapper.CustomerMapper;
import com.example.hamro_barber.model.dto.PasswordChangeDto;
import com.example.hamro_barber.service.serviceImpl.BarberServiceImpl;
import com.example.hamro_barber.service.serviceImpl.CustomerServiceImpl;
import com.example.hamro_barber.service.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final BarberServiceImpl barberService;
    private final CustomerMapper customerMapper;
    private final BarberMapper barberMapper;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        User registeredUser = userService.registerUser(signUpRequest, request);
        if (registeredUser.getUserRole().equals(UserRole.CUSTOMER)) {
            Customer customer = new Customer();
            customer.setUser(registeredUser);
            Customer registeredCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(customerMapper.customerToDto(registeredCustomer), HttpStatus.CREATED);
        } else if (registeredUser.getUserRole().equals(UserRole.BARBER)) {
            Barber barber = new Barber();
            barber.setUser(registeredUser);
            barber.setPanNo(signUpRequest.getPanNo());
            barber.setOpened(false);
            barber.setRating(0);
            barber = barberService.createBarber(barber);
            return new ResponseEntity<>(barberMapper.barberToDto(barber), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("USER ROLE NOT SELECTED", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.loginUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        emailService.send("sarthak.paneru007@gmail.com", "Hello Sarthak");
        return new ResponseEntity("Email Sent", HttpStatus.OK);
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam("email") String email
    ) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/confirm-forgot-password")
    public ResponseEntity<?> confirmAndUpdatePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        return new ResponseEntity<>(userService.confirmAndUpdatePassword(passwordChangeDto), HttpStatus.OK);
    }

}
