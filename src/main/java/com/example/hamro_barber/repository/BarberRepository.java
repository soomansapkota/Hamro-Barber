package com.example.hamro_barber.repository;

import com.example.hamro_barber.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Integer> {
    Optional<Barber> findBarberByUser_Email(String email);

    @Query(nativeQuery = true,
        value = "SELECT * from barber b\n" +
                "where b.latitude between :lowLatitude and :highLatitude \n" +
                "and b.longitude between :lowLongitude and :highLongitude")
    List<Barber> findNearestBarbers(
            @Param("highLatitude") Double highLatitude,
            @Param("lowLatitude") Double lowLatitude,
            @Param("highLongitude") Double highLongitude,
            @Param("lowLongitude") Double lowLongitude);

    @Override
    List<Barber> findAllById(Iterable<Integer> integers);
}
