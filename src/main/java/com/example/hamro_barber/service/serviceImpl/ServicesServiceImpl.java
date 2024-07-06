package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.exception.ResourceNotFoundException;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.repository.ServiceRepository;
import com.example.hamro_barber.service.BarberService;
import com.example.hamro_barber.service.ServicesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;
    private final BarberService barberService;
    @Override
    public Services createService(Services services, String email) {
        services.setBarber(barberService.findBarberByEmail(email));
        return serviceRepository.save(services);
    }

    @Override
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Services getService(Integer serviceId) {
        Optional<Services> services = serviceRepository.findById(serviceId);
        if (services.isPresent()) {
            return services.get();
        } else {
            throw new ResourceNotFoundException("No such service available");
        }
    }

    @Override
    public Services updateService(Services services) {
        Services existingServices = getService(services.getId());
        existingServices.setServiceName(services.getServiceName());
        existingServices.setFee(services.getFee());
        existingServices.setServiceTimeInMinutes(services.getServiceTimeInMinutes());
        existingServices.setCategory(services.getCategory());
        existingServices = serviceRepository.save(existingServices);
        return existingServices;
    }

    @Override
    public ApiResponse deleteService(Integer serviceId) {
        Services existingServices = getService(serviceId);
        serviceRepository.delete(existingServices);
        return new ApiResponse(true, "Service deleted successfully");
    }

    @Override
    public List<String> getCategories() {
        return serviceRepository.getCategories();
    }


}
