package com.example.hamro_barber.service;

import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Customer;
import com.example.hamro_barber.model.Favourite;
import com.example.hamro_barber.model.Services;

import java.util.List;

public interface FavouriteService {
    Favourite findFavouritesOfCustomer(Integer customerId);
    List<Barber> findFavouriteBarbers(Integer customerId);
    List<Services> findFavouriteServices(Integer customerId);
    Favourite addFavouriteBarber(Integer barberId, Customer customer);
    ApiResponse removeFavouriteBarber(Integer barberId, Customer customer);
    Favourite addFavouriteService(Integer serviceId, Customer customer);
    ApiResponse removeFavouriteService(Integer serviceId, Customer customer);
}
