package com.vaccinetracker.user.controller;

import com.vaccinetracker.booking.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;
import com.vaccinetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody final UserRequest userRequest) {
        userService.registerUser(userRequest);
        return new ResponseEntity<>("Your reservation has been scheduled", HttpStatus.CREATED);
    }

    @GetMapping("/booking/{govId}")
    @PreAuthorize("hasRole('PERSON_ROLE') || hasAuthority('SCOPE_PERSON_SCOPE')")
    public ResponseEntity<List<UserQueryWebClientResponse>> getBookingByGovId(
            @PathVariable("govId") final String govId) {
        List<UserQueryWebClientResponse> bookingQueryResponses = userService.getBookingByGovId(govId);
        return ResponseEntity.ok(bookingQueryResponses);
    }
}
