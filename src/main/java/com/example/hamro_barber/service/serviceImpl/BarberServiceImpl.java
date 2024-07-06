package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.model.Barber;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.exception.ResourceNotFoundException;
import com.example.hamro_barber.helper.ApiResponse;
import com.example.hamro_barber.repository.BarberRepository;
import com.example.hamro_barber.service.BarberService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class BarberServiceImpl implements BarberService {
    private final BarberRepository barberRepository;
    private final UserServiceImpl userService;
    private final Path path = Paths.get("images");

    @Override
    public List<Barber> getAllBarbers() {
        return barberRepository.findAll();
    }

    @Override
    public Barber findBarberById(Integer barberId) {
        Optional<Barber> barber = barberRepository.findById(barberId);
        if (barber.isPresent()) {
            return barber.get();
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
    @Override
    public List<Barber> findBarbersByIds(List<Integer> barbersId) {
        return barberRepository.findAllById(barbersId);
    }

    @Override
    public Barber findBarberByEmail(String email) {
        System.out.println(email);
        return barberRepository.findBarberByUser_Email(email).orElseThrow(() -> new CustomException("Barber with such email not found"));
    }

    @Override
    public Barber createBarber(Barber barber) {
        return barberRepository.save(barber);
    }

    @Override
    public Barber updateBarber(Barber barber) {
        User user = userService.findUserByEmail(barber.getUser().getEmail());
        Barber existingBarber = findBarberById(barber.getId());
        User user1 = userService.findUserById(barber.getUser().getId());
        User updatedUser = userService.updateUser(user1);
        existingBarber.setUser(updatedUser);
        existingBarber.setOpened(barber.isOpened());
        System.out.println("Is opened: " + barber.isOpened());
        existingBarber.setServices(barber.getServices());
        existingBarber.setPanNo(barber.getPanNo());
        return barberRepository.save(existingBarber);
    }

    @Override
//    @Transactional(rollbackFor = SQLException.class)
    public ApiResponse deleteBarber(Integer customerId) {
        Optional<Barber> barber = barberRepository.findById(customerId);
        if (barber.isPresent()) {
            barberRepository.delete(barber.get());
//            userService.deleteUser(barber.get().getUser().getId());
            return new ApiResponse(true, "User successfully deleted");
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void saveImage(MultipartFile file, Integer barberId) {
        try {
            Files.copy(file.getInputStream(), this.path.resolve(file.getOriginalFilename()));
            Barber barber = findBarberById(barberId);
            barber.setImageUrl("images/" + file.getOriginalFilename());
            barberRepository.save(barber);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String load(Integer barberId) {
        Barber barber = findBarberById(barberId);
        String imageUrl = barber.getImageUrl();
        System.out.println(imageUrl);
        try {
            File file = new ClassPathResource(
                    "static/" + imageUrl).getFile();
            System.out.println(file.toURI());
            Resource resource = new UrlResource(file.toURI());

            if (resource.exists() || resource.isReadable()) {
                System.out.println("Img: " + imageUrl);
                return imageUrl;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Barber> findNearestBarbers(Double latitude, Double longitude) {
        List<Barber> barbers = barberRepository.findNearestBarbers(latitude+0.03, latitude-0.03, longitude+0.03, longitude-0.03);
        Map<Integer, Double> map = new HashMap<>();
        Map<Integer, Double> sortedMap = new HashMap<>();
        ArrayList<Double> list = new ArrayList<>();
        for (Barber barber: barbers) {
            Double dist = findDistance(latitude, longitude, barber.getLatitude().doubleValue(), barber.getLongitude().doubleValue());
            System.out.println(barber.getId() + ": " + dist);
            map.put(barber.getId(), dist);
        }
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        list.sort(new Comparator<Double>() {
            public int compare(Double str, Double str1) {
                return (str).compareTo(str1);
            }
        });

        List<Barber> barberList = new ArrayList<>();
        for (Double str : list) {
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                if (entry.getValue().equals(str)) {
                    sortedMap.put(entry.getKey(), str);
                    Barber barber = barberRepository.findById(entry.getKey()).get();
                    barber.setDistance(str);
                    barberList.add(barber);
                }
            }
        }


        return barberList;
    }

    private Double findDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        System.out.println(lon1+ "  " + lon2 + "  " + lat1 + "  " + lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        System.out.println("a = " + a);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers
        double r = 6371;
        System.out.println("c*r = " + c*r);

        // calculate the result
        return (c * r);
    }
}
