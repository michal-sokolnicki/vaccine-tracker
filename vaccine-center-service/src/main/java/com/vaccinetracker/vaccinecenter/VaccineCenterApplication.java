package com.vaccinetracker.vaccinecenter;

import com.vaccinetracker.kafka.admin.service.Initializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.vaccinetracker")
public class VaccineCenterApplication implements CommandLineRunner {

    private final Initializer initializer;

    public static void main(String[] args) {
        SpringApplication.run(VaccineCenterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializer.init();
    }
}
