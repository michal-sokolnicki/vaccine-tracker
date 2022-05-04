package com.vaccinetracker.query.controller;

import com.vaccinetracker.query.model.BookingQueryRequest;
import com.vaccinetracker.query.model.BookingQueryResponse;
import com.vaccinetracker.query.model.VaccineCenterQueryResponse;
import com.vaccinetracker.query.service.BookingQueryService;
import com.vaccinetracker.query.service.VaccineCenterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine")
@PreAuthorize("isAuthenticated()")
public class QueryServiceController {

    private final BookingQueryService bookingQueryService;
    private final VaccineCenterQueryService vaccineCenterQueryService;

    @GetMapping("/booking/owned/{govId}")
    @PreAuthorize("hasAuthority('SCOPE_PERSON_SCOPE')")
    public ResponseEntity<List<BookingQueryResponse>> getBookingByGovId(
            @PathVariable("govId") final String govId, @RequestParam(required = false) final String status) {
        List<BookingQueryResponse> bookingQueryResponses = Optional.ofNullable(status)
                .map(s -> bookingQueryService.getBookingByGovIdAndStatus(govId, s))
                .orElseGet(() -> bookingQueryService.getBookingByGovId(govId));
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    public ResponseEntity<BookingQueryResponse> getBookingById(@PathVariable("id") final String id) {
        BookingQueryResponse bookingQueryResponse = bookingQueryService.getBookingById(id);
        return ResponseEntity.ok(bookingQueryResponse);
    }

    @PostMapping("/booking/range")
    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    public ResponseEntity<List<BookingQueryResponse>> getBookingByDateRange(
            @RequestBody final BookingQueryRequest bookingQueryRequest) {
        List<BookingQueryResponse> bookingQueryResponses =
                bookingQueryService.getBookingByDateRange(bookingQueryRequest);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @GetMapping("/booking/search")
    @PreAuthorize("hasRole('PERSON_ROLE')")
    public ResponseEntity<List<BookingQueryResponse>> searchBookingByText(
            @RequestParam("text") final String text) {
        List<BookingQueryResponse> bookingQueryResponses = bookingQueryService.searchByText(text);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @GetMapping("/vaccinecenter/{id}")
    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    public ResponseEntity<VaccineCenterQueryResponse> getVaccineCenterById(
            @PathVariable("id") final String id) {
        VaccineCenterQueryResponse vaccineCenterQueryResponse =
                vaccineCenterQueryService.getVaccineCenterById(id);
        return ResponseEntity.ok(vaccineCenterQueryResponse);
    }

    @GetMapping("/vaccinecenter/search")
    @PreAuthorize("hasRole('PERSON_ROLE')")
    public ResponseEntity<List<VaccineCenterQueryResponse>> searchVaccineCenterByText(
            @RequestParam("text") final String text) {
        List<VaccineCenterQueryResponse> vaccineCenterQueryResponses =
                vaccineCenterQueryService.searchByText(text);
        return ResponseEntity.ok(vaccineCenterQueryResponses);
    }
}
