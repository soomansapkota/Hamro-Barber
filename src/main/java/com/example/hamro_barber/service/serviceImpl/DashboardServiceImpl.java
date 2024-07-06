package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.exception.BadRequestException;
import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.helper.AuthResponse;
import com.example.hamro_barber.helper.LoginRequest;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.repository.ServiceRepository;
import com.example.hamro_barber.repository.UserRepository;
import com.example.hamro_barber.security.TokenProvider;
import com.example.hamro_barber.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {



    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    @Override
    public List<Map<String, Object>> getCountsByCategory(Integer barberId) {

        return serviceRepository.getCountsByCategory(barberId);
    }

    @Override
    public List<Map<String, Object>> getTopCustomerByCategory(Integer barberId) {
        return serviceRepository.getTopCustomerByCategory(barberId);
    }

    @Override
    public List<Map<String, Object>> getTopCustomerByService(Integer barberId) {
        return serviceRepository.getCustomerByServiceName(barberId);
    }

    @Override
    public List<Map<String, Object>> getCountsByServiceName(Integer barberId) {
        return serviceRepository.getCountsByServiceName(barberId);
    }
    @Override
    public List<Map<String, Object>> getCountsByCategoryForAppointment(Integer barberId) {
        return serviceRepository.getCountsByCategoryForAppointment(barberId);
    }

    @Override
    public List<Map<String, Object>> getCountsByServiceNameForAppointment(Integer barberId) {
        return serviceRepository.getCountsByServiceNameForAppointment(barberId);
    }

}

//    public List<Map<String, Object>> getServiceCountsByBarberAndCategory() {
//        List<Object[]> results = ServiceRepository.getServiceCountsByBarberAndCategory();
//
//        return results.stream()
//                .map(row -> {
//                    Long barberId = (Long) row[0];
//                    String category = (String) row[1];
//                    String serviceName = (String) row[2];
//                    Long serviceCount = (Long) row[3];
//
//                    return Map.of(
//                            "barberId", barberId,
//                            "category", category,
//                            "serviceName", serviceName,
//                            "serviceCount", serviceCount
//                    );
//                })
//                .collect(Collectors.toList());
//    }


