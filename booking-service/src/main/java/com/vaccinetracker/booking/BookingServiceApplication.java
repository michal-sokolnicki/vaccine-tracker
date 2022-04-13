package com.vaccinetracker.booking;

import com.vaccinetracker.booking.service.StreamInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vaccinetracker")
public class BookingServiceApplication implements CommandLineRunner {

    private final StreamInitializer streamInitializer;

    public BookingServiceApplication(StreamInitializer streamInitializer) {
        this.streamInitializer = streamInitializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        streamInitializer.init();
    }
}
