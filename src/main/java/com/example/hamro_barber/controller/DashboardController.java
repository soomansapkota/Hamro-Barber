package com.example.hamro_barber.controller;

import com.example.hamro_barber.mapper.ServiceMapper;
import com.example.hamro_barber.service.serviceImpl.DashboardServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/analytics")
public class DashboardController {
    private final DashboardServiceImpl dashboardService;
    private final ServiceMapper serviceMapper;

    @GetMapping("/getCategory")
    public ResponseEntity<List<Map<String, Object>>> getCountsByCategory(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getCountsByCategory(barberId);
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/getServiceName")
    public ResponseEntity<List<Map<String, Object>>> getCountsByServiceName(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getCountsByServiceName(barberId);
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/getCategoryForAppointment")
    public ResponseEntity<List<Map<String, Object>>> getCategoryForAppointment(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getCountsByCategoryForAppointment(barberId);
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/getServiceNameForAppointment")
    public ResponseEntity<List<Map<String, Object>>> getServiceNameForAppointment(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getCountsByServiceNameForAppointment(barberId);
        return ResponseEntity.ok(getData);
    }
    @GetMapping("/getTopCustomerByCategory")
    public ResponseEntity<List<Map<String, Object>>> getTopCustomerByCategory(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getTopCustomerByCategory(barberId);
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/getTopCustomerByService")
    public ResponseEntity<List<Map<String, Object>>> getTopCustomerByService(@RequestParam Integer barberId) {
        List<Map<String, Object>> getData = dashboardService.getTopCustomerByService(barberId);
        return ResponseEntity.ok(getData);
    }

}
