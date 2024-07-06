package com.example.hamro_barber.controller;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.dto.BarberDto;
import com.example.hamro_barber.mapper.BarberMapper;
import com.example.hamro_barber.model.dto.LocationDto;
import com.example.hamro_barber.service.serviceImpl.BarberServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/barber")
@AllArgsConstructor
public class BarberController {
    private final BarberServiceImpl barberService;
    private final BarberMapper barberMapper;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBarbers() {
        return new ResponseEntity<>(barberMapper.listBarberToDto(barberService.getAllBarbers()), HttpStatus.OK);
    }

    @GetMapping("/get/{barberId}")
    public ResponseEntity<?> getBarber(@PathVariable Integer barberId) {
        return new ResponseEntity<>(barberMapper.barberToDto(barberService.findBarberById(barberId)), HttpStatus.OK);
    }

    @GetMapping("/get-logged-in-user")
    public ResponseEntity<?> getLoggedInUser(Principal principal) {
        return new ResponseEntity<>(barberMapper.barberToDto(barberService.findBarberByEmail(principal.getName())), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBarber(@RequestBody Barber barber) {
        return new ResponseEntity<>(barberMapper.barberToDto(barberService.createBarber(barber)), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBarber(@RequestBody BarberDto barber) {
        return new ResponseEntity<>(barberMapper.barberToDto(barberService.updateBarber(barberMapper.dtoToBarber(barber))), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{barberId}")
    public ResponseEntity<?> deletedBarber(@PathVariable Integer barberId) {
        return new ResponseEntity<>(barberService.deleteBarber(barberId), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{barberId}/update-image")
    public ResponseEntity<?> updateImage(MultipartFile file, @PathVariable Integer barberId) {
        barberService.saveImage(file, barberId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/{barberId}/get-image")
    public void getImage(@PathVariable Integer barberId, HttpServletResponse response) throws IOException {
        barberService.findBarberById(barberId);
        response.sendRedirect(barberService.load(barberId));
    }

    @PutMapping("/{barberId}/update/location")
    public ResponseEntity<?> updateLocation(@PathVariable Integer barberId, @RequestBody BarberDto barber) {
        Barber existingBarber = barberService.findBarberById(barberId);
        existingBarber.setLatitude(barber.getLatitude());
        existingBarber.setLongitude(barber.getLongitude());
        existingBarber = barberService.updateBarber(existingBarber);
        return new ResponseEntity<>(barberMapper.barberToDto(existingBarber), HttpStatus.OK);
    }

    @GetMapping("/get/nearest")
    public ResponseEntity<?> getNearestBarbers(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        System.out.println(latitude);
        System.out.println(longitude);
        List<Barber> barbers = barberService.findNearestBarbers(latitude, longitude);
        return new ResponseEntity<>(barberMapper.listBarberToDto(barbers), HttpStatus.OK);
    }
}
