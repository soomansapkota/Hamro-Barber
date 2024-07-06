package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.model.Favourite;
import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.repository.FavouriteRepository;
import com.example.hamro_barber.service.FavouriteService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private FavouriteRepository favouriteRepository;
    private BarberServiceImpl barberService;
    private ServicesServiceImpl servicesService;

    @Override
    public Favourite findFavouritesOfCustomer(Integer customerId) {
        Optional<Favourite> favourite = favouriteRepository.findAllByCustomer_Id(customerId);

        if (favourite.isEmpty()) {
            throw new CustomException("No favourites of the customer");
        }
        return favourite.get();
    }

    @Override
    public List<Barber> findFavouriteBarbers(Integer customerId) {
        System.out.println(customerId);
        List<Integer> barbersId = favouriteRepository.findAllFavouriteBarbersOfCustomer(customerId);
        System.out.println("IDS: " + barbersId);
        return barberService.findBarbersByIds(barbersId);
    }

    @Override
    public List<Services> findFavouriteServices(Integer customerId) {
        return null;
    }

    @Override
    public Favourite addFavouriteBarber(Integer barberId, Customer customer) {
        Favourite existingFavourite = findFavouritesOfCustomer(customer.getId());

        if (existingFavourite == null) {

            Favourite favourite = new Favourite();
            favourite.setCustomer(customer);

            Barber barber = barberService.findBarberById(barberId);
            List<Barber> barbers = List.of(barber);

            favourite.setBarbers(barbers);

            return favouriteRepository.save(favourite);
        }
        List<Barber> barbers = findFavouriteBarbers(customer.getId());
        Barber barber = barberService.findBarberById(barberId);
        if (!barbers.contains(barber)) {
            if (barbers.isEmpty()) {
                barbers = List.of(barber);
            } else {
                barbers.add(barber);
            }
        }
        existingFavourite.setBarbers(barbers);

        return favouriteRepository.save(existingFavourite);
    }

    @Override
    public ApiResponse removeFavouriteBarber(Integer barberId, Customer customer) {
        Favourite favourite = findFavouritesOfCustomer(customer.getId());
        favourite.setCustomer(customer);

        List<Barber> barbers = favourite.getBarbers();
        Barber barber = barberService.findBarberById(barberId);

        assert barbers != null;
        barbers.remove(barber);
        favourite.setBarbers(barbers);

        favouriteRepository.save(favourite);

        return new ApiResponse(true, "Barber removed from favourite lists");
    }

    @Override
    public Favourite addFavouriteService(Integer serviceId, Customer customer) {

        Favourite existingFavourite = findFavouritesOfCustomer(customer.getId());

        if (existingFavourite == null) {

            Favourite favourite = new Favourite();
            favourite.setCustomer(customer);

            Services service = servicesService.getService(serviceId);
            List<Services> services = List.of(service);

            favourite.setServices(services);

            return favouriteRepository.save(favourite);
        }
        List<Services> services = findFavouriteServices(customer.getId());
        Services service = servicesService.getService(serviceId);
        if (!services.contains(service)) {
            if (services.isEmpty()) {
                services = List.of(service);
            } else {
                services.add(service);
            }
        }
        existingFavourite.setServices(services);

        return favouriteRepository.save(existingFavourite);

    }

    @Override
    public ApiResponse removeFavouriteService(Integer serviceId, Customer customer) {
        Favourite favourite = findFavouritesOfCustomer(customer.getId());
        favourite.setCustomer(customer);

        List<Services> services = favourite.getServices();
        Services service = servicesService.getService(serviceId);

        assert services != null;
        services.remove(service);
        favourite.setServices(services);

        favouriteRepository.save(favourite);

        return new ApiResponse(true, "Successfully removed Service from favourite lists");
    }
}
