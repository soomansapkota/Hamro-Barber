package com.example.hamro_barber.service;

import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.helper.ApiResponse;

import java.util.List;

public interface ServicesService {
    Services createService(Services services, String email);
    List<Services> getAllServices();
    Services getService(Integer serviceId);
    Services updateService(Services services);
    ApiResponse deleteService(Integer serviceId);
    List<String> getCategories();

}
