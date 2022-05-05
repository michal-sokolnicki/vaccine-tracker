package com.vaccinetracker.user.controller;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;
import com.vaccinetracker.user.service.UserService;
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
        return new ResponseEntity<>(MessageFormat.format("Your {0} has been created", userRequest.getUsername()),
                HttpStatus.CREATED);
    }

    @GetMapping("/booking/{govId}")
    @PreAuthorize("isAuthenticated() AND hasRole('PERSON_ROLE') OR hasAuthority('SCOPE_PERSON_SCOPE')")
    public ResponseEntity<List<UserQueryWebClientResponse>> getBookingByGovId(
            @PathVariable("govId") final String govId) {
        List<UserQueryWebClientResponse> bookingQueryResponses = userService.getBookingByGovId(govId);
        return ResponseEntity.ok(bookingQueryResponses);
    }
}
