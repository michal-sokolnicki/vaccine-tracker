package com.vaccinetracker.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = "com.vaccinetracker")
public class UserServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
