package com.vaccinetracker.gateway.controller;

import com.vaccinetracker.gateway.model.FallbackModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @Value("${server.port}")
    private String port;

    @PostMapping("/query-fallback")
    public ResponseEntity<FallbackModel> queryServiceFallback() {
        log.info("Returning fallback result for query-service! on port {}", port);
        return ResponseEntity.ok(FallbackModel.builder()
                .fallbackMessage("Fallback result for query-service!")
                .build());
    }

    @PostMapping("/booking-fallback")
    public ResponseEntity<FallbackModel> bookingServiceFallback() {
        log.info("Returning fallback result for booking-service! on port {}", port);
        return ResponseEntity.ok(FallbackModel.builder()
                .fallbackMessage("Fallback result for booking-service!")
                .build());
    }


    @PostMapping("/user-fallback")
    public ResponseEntity<FallbackModel> userServiceFallback() {
        log.info("Returning fallback result for user-service! on port {}", port);
        return ResponseEntity.ok(FallbackModel.builder()
                .fallbackMessage("Fallback result for user-service!")
                .build());
    }

    @PostMapping("/vaccine-center-fallback")
    public ResponseEntity<FallbackModel> vaccineCenterServiceFallback() {
        log.info("Returning fallback result for vaccine-center-service! on port {}", port);
        return ResponseEntity.ok(FallbackModel.builder()
                .fallbackMessage("Fallback result for vaccine-center-service!")
                .build());
    }
}
