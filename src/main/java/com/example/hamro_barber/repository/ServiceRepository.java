package com.example.hamro_barber.repository;

import com.example.hamro_barber.model.Services;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.model.dto.ServiceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Services, Integer> {



    @Query(nativeQuery = true,
        value = "SELECT DISTINCT(category) FROM services")
    List<String> getCategories();

    @Query(nativeQuery = true,
            value = "SELECT \n" +
                    "    category, \n" +
                    "    COUNT(*) AS servicecount,\n" +
                    "    (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM services WHERE barber_id = ?1)) AS percentage\n" +
                    "FROM \n" +
                    "    services \n" +
                    "WHERE \n" +
                    "    barber_id = ?1\n" +
                    "GROUP BY \n" +
                    "    category\n" +
                    "ORDER BY \n" +
                    "servicecount DESC;\n")
    List<Map<String,Object>> getCountsByCategory(Integer barber_id);

    @Query(nativeQuery = true,
            value = "SELECT \n" +
                    "    category, \n" +
                    "    COUNT(*) AS servicecount,\n" +
                    "    (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM services WHERE barber_id = ?1)) AS percentage\n" +
                    "FROM \n" +
                    "    services \n" +
                    "WHERE \n" +
                    "    barber_id = ?1\n" +
                    "GROUP BY \n" +
                    "    category\n" +
                    "ORDER BY \n" +
                    "    servicecount DESC;\n")
    List<Map<String,Object>> getCountsByServiceName(Integer barber_id);

//    @Query(nativeQuery = true,
//            value = "SELECT s.category, COUNT(DISTINCT aps.appointment_id) AS appointment_count" +
//                    " FROM appointment_services AS aps JOIN services AS s ON aps.services_id = s.id " +
//                    "WHERE barber_id=?1 " +
//                    "GROUP BY s.category " +
//                    "ORDER BY appointment_count " +
//                    "DESC")
//    List<Map<String,Object>> getCountsByCategoryForAppointment(Integer barber_id);

    @Query(nativeQuery = true,
            value = "WITH TotalAppointments AS (\n" +
                    "    SELECT COUNT(DISTINCT aps.appointment_id) AS total\n" +
                    "    FROM appointment_services AS aps\n" +
                    "    JOIN services AS s ON aps.services_id = s.id\n" +
                    "    WHERE s.barber_id = ?1\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    s.category, \n" +
                    "    COUNT(DISTINCT aps.appointment_id) AS appointment_count,\n" +
                    "    (COUNT(DISTINCT aps.appointment_id) * 100.0 / (SELECT total FROM TotalAppointments)) AS appointment_percentage\n" +
                    "FROM appointment_services AS aps\n" +
                    "JOIN services AS s ON aps.services_id = s.id\n" +
                    "WHERE s.barber_id = ?1\n" +
                    "GROUP BY s.category\n" +
                    "ORDER BY appointment_percentage DESC;\n")
    List<Map<String,Object>> getCountsByCategoryForAppointment(Integer barber_id);


    @Query(nativeQuery = true,
            value = "WITH TotalAppointments AS (\n" +
                    "    SELECT COUNT(DISTINCT aps.appointment_id) AS total\n" +
                    "    FROM appointment_services AS aps\n" +
                    "    JOIN services AS s ON aps.services_id = s.id\n" +
                    "    WHERE barber_id = ?1\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    s.service_name, \n" +
                    "    COUNT(DISTINCT aps.appointment_id) AS appointmentCount,\n" +
                    "    (COUNT(DISTINCT aps.appointment_id) * 100.0 / (SELECT total FROM TotalAppointments)) AS appointmentPercentage\n" +
                    "FROM appointment_services AS aps\n" +
                    "JOIN services AS s ON aps.services_id = s.id\n" +
                    "WHERE s.barber_id = ?1\n" +
                    "GROUP BY s.service_name\n" +
                    "ORDER BY appointmentPercentage DESC\n")
    List<Map<String,Object>> getCountsByServiceNameForAppointment(Integer barber_id);
//    @Query(nativeQuery = true,
//            value = "SELECT s.service_name, COUNT(DISTINCT aps.appointment_id) as appointmentCount " +
//                    "FROM hamrobarber.appointment_services AS aps " +
//                    "JOIN  services AS s ON aps.services_id = s.id " +
//                    "WHERE barber_id=?1 " +
//                    "GROUP BY s.service_name " +
//                    "ORDER BY appointmentCount DESC")
//    List<Map<String,Object>> getCountsByServiceNameForAppointment(Integer barber_id);

    @Query(nativeQuery = true, value =
            "WITH category_totals AS ( " +
                    "    SELECT s.category, COUNT(aps.services_id) AS total_count " +
                    "    FROM appointment a " +
                    "    JOIN appointment_services aps ON a.id = aps.appointment_id " +
                    "    JOIN services s ON aps.services_id = s.id " +
                    "    WHERE a.barber_id = :barberId " +
                    "    GROUP BY s.category " +
                    "), " +
                    "ranked_customers AS ( " +
                    "    SELECT s.category, u.first_name, u.last_name, COUNT(aps.services_id) AS category_count, " +
                    "    (COUNT(aps.services_id) * 100.0 / ct.total_count) AS percentage, " +
                    "    ROW_NUMBER() OVER (PARTITION BY s.category ORDER BY COUNT(aps.services_id) DESC) AS rn " +
                    "    FROM appointment a " +
                    "    JOIN appointment_services aps ON a.id = aps.appointment_id " +
                    "    JOIN services s ON aps.services_id = s.id " +
                    "    JOIN customer c ON a.customer_id = c.id " +
                    "    JOIN user u ON c.user_id = u.id " +
                    "    JOIN category_totals ct ON s.category = ct.category " +
                    "    WHERE a.barber_id = :barberId " +
                    "    GROUP BY s.category, u.first_name, u.last_name, ct.total_count " +
                    ") " +
                    "SELECT category, first_name, last_name, category_count, percentage " +
                    "FROM ranked_customers " +
                    "WHERE rn <= 5 " +
                    "ORDER BY category, category_count DESC")
    List<Map<String, Object>> getTopCustomerByCategory(Integer barberId);


    @Query(nativeQuery = true, value =
            "WITH service_totals AS ( " +
                    "    SELECT s.category, s.service_name, COUNT(aps.services_id) AS total_count " +
                    "    FROM appointment a " +
                    "    JOIN appointment_services aps ON a.id = aps.appointment_id " +
                    "    JOIN services s ON aps.services_id = s.id " +
                    "    WHERE a.barber_id = :barberId " +
                    "    GROUP BY s.category, s.service_name " +
                    "), " +
                    "ranked_customers AS ( " +
                    "    SELECT s.category, s.service_name, u.first_name, u.last_name, COUNT(aps.services_id) AS service_count, " +
                    "    (COUNT(aps.services_id) * 100.0 / st.total_count) AS percentage, " +
                    "    ROW_NUMBER() OVER (PARTITION BY s.category, s.service_name ORDER BY COUNT(aps.services_id) DESC) AS rn " +
                    "    FROM appointment a " +
                    "    JOIN appointment_services aps ON a.id = aps.appointment_id " +
                    "    JOIN services s ON aps.services_id = s.id " +
                    "    JOIN customer c ON a.customer_id = c.id " +
                    "    JOIN user u ON c.user_id = u.id " +
                    "    JOIN service_totals st ON s.service_name = st.service_name AND s.category = st.category " +
                    "    WHERE a.barber_id = :barberId " +
                    "    GROUP BY s.category, s.service_name, u.first_name, u.last_name, st.total_count " +
                    ") " +
                    "SELECT category, service_name, first_name, last_name, service_count, percentage " +
                    "FROM ranked_customers " +
                    "WHERE rn <= 5 " +
                    "ORDER BY category, service_name, service_count DESC")
    List<Map<String, Object>> getCustomerByServiceName(Integer barberId);



}
