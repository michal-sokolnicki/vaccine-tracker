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

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine")
@PreAuthorize("isAuthenticated()")
public class QueryServiceController {

    private final BookingQueryService bookingQueryService;
    private final VaccineCenterQueryService vaccineCenterQueryService;

    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingQueryResponse> getBookingById(@PathVariable("id") final String id) {
        BookingQueryResponse bookingQueryResponse = bookingQueryService.getBookingById(id);
        return ResponseEntity.ok(bookingQueryResponse);
    }

    @PreAuthorize("hasRole('PERSON_ROLE')")
    @GetMapping("/booking/{govId}/history")
    public ResponseEntity<List<BookingQueryResponse>> getBookingHistoryByGovId(
            @PathVariable("govId") final String govId) {
        List<BookingQueryResponse> bookingQueryResponses = bookingQueryService.getBookingHistoryByGovId(govId);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @PreAuthorize("hasRole('PERSON_ROLE')")
    @GetMapping("/booking/{govId}/{status}")
    public ResponseEntity<List<BookingQueryResponse>> getBookingByGovIdAndStatus(
            @PathVariable("govId") final String govId, @PathVariable("status") final String status) {
        List<BookingQueryResponse> bookingQueryResponses =
                bookingQueryService.getBookingByGovIdAndStatus(govId, status);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    @PostMapping("/booking/range")
    public ResponseEntity<List<BookingQueryResponse>> getBookingByDateRange(
            @RequestBody final BookingQueryRequest bookingQueryRequest) {
        List<BookingQueryResponse> bookingQueryResponses =
                bookingQueryService.getBookingByDateRange(bookingQueryRequest);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @PreAuthorize("hasRole('PERSON_ROLE')")
    @GetMapping("/booking/search")
    public ResponseEntity<List<BookingQueryResponse>> searchBookingByText(
            @RequestParam("text") final String text) {
        List<BookingQueryResponse> bookingQueryResponses = bookingQueryService.searchByText(text);
        return ResponseEntity.ok(bookingQueryResponses);
    }

    @PreAuthorize("hasRole('PERSON_ROLE') || hasRole('VACCINE_CENTER_USER_ROLE')")
    @GetMapping("/vaccinecenter/{id}")
    public ResponseEntity<VaccineCenterQueryResponse> getVaccineCenterById(
            @PathVariable("id") final String id) {
        VaccineCenterQueryResponse vaccineCenterQueryResponse =
                vaccineCenterQueryService.getVaccineCenterById(id);
        return ResponseEntity.ok(vaccineCenterQueryResponse);
    }

    @PreAuthorize("hasRole('PERSON_ROLE')")
    @GetMapping("/vaccinecenter/search")
    public ResponseEntity<List<VaccineCenterQueryResponse>> searchVaccineCenterByText(
            @RequestParam("text") final String text) {
        List<VaccineCenterQueryResponse> vaccineCenterQueryResponses =
                vaccineCenterQueryService.searchByText(text);
        return ResponseEntity.ok(vaccineCenterQueryResponses);
    }
}
