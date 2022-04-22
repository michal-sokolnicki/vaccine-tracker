package com.vaccinetracker.booking;

import com.vaccinetracker.booking.service.Initializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vaccinetracker")
public class BookingServiceApplication implements CommandLineRunner {

    private final Initializer initializer;

    public BookingServiceApplication(Initializer initializer) {
        this.initializer = initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializer.init();
    }
}
