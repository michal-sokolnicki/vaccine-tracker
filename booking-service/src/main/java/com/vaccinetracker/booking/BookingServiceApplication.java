package com.vaccinetracker.booking;

import com.vaccinetracker.kafka.admin.service.Initializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.vaccinetracker")
public class BookingServiceApplication implements CommandLineRunner {

    private final Initializer initializer;

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializer.init();
    }
}
