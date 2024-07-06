package com.example.hamro_barber.repository;

import com.example.hamro_barber.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer userId);
//    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
