package com.example.hamro_barber.service;

import com.example.hamro_barber.helper.LoginRequest;

import java.util.List;
import java.util.Map;


public interface DashboardService {


    List<Map<String, Object>> getCountsByCategory(Integer barberId);
    List<Map<String, Object>> getCountsByServiceName(Integer barberId);
    List<Map<String,Object>> getCountsByCategoryForAppointment(Integer barberId);
    List<Map<String,Object>> getCountsByServiceNameForAppointment(Integer barberId);
    List<Map<String,Object>> getTopCustomerByCategory(Integer barberId);
    List<Map<String,Object>> getTopCustomerByService(Integer barberId);

}
