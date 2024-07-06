package com.example.hamro_barber;

import com.example.hamro_barber.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class HamroBarberApplication {

    public static void main(String[] args) {
        SpringApplication.run(HamroBarberApplication.class, args);
    }

}
