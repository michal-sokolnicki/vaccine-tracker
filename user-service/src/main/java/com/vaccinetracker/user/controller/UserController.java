package com.vaccinetracker.user.controller;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.service.UserService;
import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;
import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody final UserRequest userRequest) {
        userService.registerUser(userRequest);
        return new ResponseEntity<>(MessageFormat.format("User: {0} has been created", userRequest.getUsername()),
                HttpStatus.CREATED);
    }

    @GetMapping("/booking/{govId}")
    @PreAuthorize("isAuthenticated() AND hasRole('PERSON_ROLE')")
    public ResponseEntity<List<BookingQueryWebClientResponse>> getBookingByGovId(
            @PathVariable("govId") final String govId) {
        List<BookingQueryWebClientResponse> bookingQueryResponses = userService.getBookingByGovId(govId);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @GetMapping("/vaccinecenter/search")
    @PreAuthorize("isAuthenticated() AND hasRole('PERSON_ROLE')")
    public ResponseEntity<List<VaccineCenterQueryWebClientResponse>> searchVaccineCenterByText(
            @RequestParam("text") final String text) {
        List<VaccineCenterQueryWebClientResponse> vaccineCenterQueryResponses =
                userService.searchVaccineCenterByText(text);
        return ResponseEntity.ok(vaccineCenterQueryResponses);
    }
}
