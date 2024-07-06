package com.example.hamro_barber.repository;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    Optional<Favourite> findAllByCustomer_Id(Integer customerId);
    @Query(nativeQuery = true,
        value = "SELECT f.barbers_id from favourite_barbers f \n" +
                "join favourite f2 ON f.favourite_id = f2.id \n" +
                "where f2.customer_id  like :customerId")
    List<Integer> findAllFavouriteBarbersOfCustomer(@Param("customerId") Integer customerId);
}
