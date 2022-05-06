package com.vaccinetracker.vaccinecenter.controller;

import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine/vaccinecenter")
public class VaccineCenterController {

    private final VaccineCenterService vaccineCenterService;

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() AND hasRole('VACCINE_CENTER_USER_ROLE')")
    public ResponseEntity<String> updateStock(@PathVariable("id") final String id,
                                                @RequestBody final VaccineCenterRequest vaccineCenterRequest) {
        vaccineCenterService.updateStock(id, vaccineCenterRequest);
        return new ResponseEntity<>("Vaccine center stock has been updated", HttpStatus.ACCEPTED);
    }
}
