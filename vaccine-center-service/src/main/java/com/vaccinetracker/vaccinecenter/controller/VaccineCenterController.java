package com.vaccinetracker.vaccinecenter.controller;

import com.vaccinetracker.vaccinecenter.model.UserRequest;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine/vaccinecenter")
@PreAuthorize("isAuthenticated()")
public class VaccineCenterController {

    private final VaccineCenterService vaccineCenterService;

    @PostMapping("/registration")
    @PreAuthorize("hasRole('VACCINE_CENTER_ADMIN_ROLE')")
    public ResponseEntity<String> registerUser(@RequestBody final UserRequest userRequest) {
        vaccineCenterService.registerUser(userRequest);
        return new ResponseEntity<>(MessageFormat.format("User: {0} has been created", userRequest.getUsername()),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VACCINE_CENTER_USER_ROLE')")
    public ResponseEntity<String> updateStock(@PathVariable("id") final String id,
                                                @RequestBody final VaccineCenterRequest vaccineCenterRequest) {
        vaccineCenterService.updateStock(id, vaccineCenterRequest);
        return new ResponseEntity<>("Vaccine center stock has been updated", HttpStatus.ACCEPTED);
    }
}
