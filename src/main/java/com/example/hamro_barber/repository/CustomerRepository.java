package com.example.hamro_barber.repository;

import com.example.hamro_barber.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(nativeQuery = true,
        value = "SELECT * from customer where user_id like :id")
    Optional<Customer> findByUserId(@Param("id") Integer id);
}
