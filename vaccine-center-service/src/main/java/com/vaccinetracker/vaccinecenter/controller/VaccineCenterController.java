package com.vaccinetracker.vaccinecenter.controller;

import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import com.vaccinetracker.vaccinecenter.service.VaccineCenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccine/vaccinecenter")
public class VaccineCenterController {

    private final VaccineCenterService vaccineCenterService;

    public VaccineCenterController(VaccineCenterService vaccineCenterService) {
        this.vaccineCenterService = vaccineCenterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStock(@PathVariable("id") final String id,
                                                @RequestBody final VaccineCenterRequest vaccineCenterRequest) {
        vaccineCenterService.updateStock(id, vaccineCenterRequest);
        return new ResponseEntity<>("Vaccine center stock has been updated", HttpStatus.ACCEPTED);
    }
}
