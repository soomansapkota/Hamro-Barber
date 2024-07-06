package com.example.hamro_barber.service;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.helper.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BarberService {
    List<Barber> getAllBarbers();
    Barber findBarberById(Integer barberId);
    List<Barber> findBarbersByIds(List<Integer> barbersId);
    Barber findBarberByEmail(String email);
    Barber createBarber(Barber barber);
    Barber updateBarber(Barber barber);
    ApiResponse deleteBarber(Integer barberId);
    void saveImage(MultipartFile file, Integer barberId);
    String load(Integer barberId);

    List<Barber> findNearestBarbers(Double latitude, Double longitude);
}
