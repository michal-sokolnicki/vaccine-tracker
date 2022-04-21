package com.vaccinetracker.query.controller;

import com.vaccinetracker.query.model.BookingQueryServiceResponseModel;
import com.vaccinetracker.query.model.VaccineCenterQueryServiceResponseModel;
import com.vaccinetracker.query.service.BookingQueryService;
import com.vaccinetracker.query.service.VaccineCenterQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class QueryServiceController {

    private final BookingQueryService bookingQueryService;
    private final VaccineCenterQueryService vaccineCenterQueryService;

    public QueryServiceController(BookingQueryService bookingQueryService, VaccineCenterQueryService vaccineCenterQueryService) {
        this.bookingQueryService = bookingQueryService;
        this.vaccineCenterQueryService = vaccineCenterQueryService;
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingQueryServiceResponseModel> getBookingById(@PathVariable("id") final String id) {
        BookingQueryServiceResponseModel bookingQueryServiceResponseModel = bookingQueryService.getBookingById(id);
        return ResponseEntity.ok(bookingQueryServiceResponseModel);
    }

    @GetMapping("/booking/{govId}/history")
    public ResponseEntity<List<BookingQueryServiceResponseModel>> getBookingHistoryByGovId(
            @PathVariable("govId") final String govId) {
        List<BookingQueryServiceResponseModel> bookingQueryServiceResponseModels = bookingQueryService.getBookingHistoryByGovId(govId);
        return ResponseEntity.ok(bookingQueryServiceResponseModels);
    }

    @GetMapping("/booking/{govId}/{status}")
    public ResponseEntity<List<BookingQueryServiceResponseModel>> getBookingByGovIdAndStatus(
            @PathVariable("govId") final String govId, @PathVariable("status") final String status) {
        List<BookingQueryServiceResponseModel> bookingQueryServiceResponseModels =
                bookingQueryService.getBookingByGovIdAndStatus(govId, status);
        return ResponseEntity.ok(bookingQueryServiceResponseModels);
    }

    @GetMapping("/booking/search")
    public ResponseEntity<List<BookingQueryServiceResponseModel>> searchBookingByText(
            @RequestParam("text") final String text) {
        List<BookingQueryServiceResponseModel> bookingQueryServiceResponseModels = bookingQueryService.searchByText(text);
        return ResponseEntity.ok(bookingQueryServiceResponseModels);
    }

    @GetMapping("/vaccinecenter/{id}")
    public ResponseEntity<VaccineCenterQueryServiceResponseModel> getVaccineCenterById(
            @PathVariable("id") final String id) {
        VaccineCenterQueryServiceResponseModel vaccineCenterQueryServiceResponseModel =
                vaccineCenterQueryService.getVaccineCenterById(id);
        return ResponseEntity.ok(vaccineCenterQueryServiceResponseModel);
    }

    @GetMapping("/vaccinecenter/search")
    public ResponseEntity<List<VaccineCenterQueryServiceResponseModel>> searchVaccineCenterByText(
            @RequestParam("text") final String text) {
        List<VaccineCenterQueryServiceResponseModel> vaccineCenterQueryServiceResponseModels =
                vaccineCenterQueryService.searchByText(text);
        return ResponseEntity.ok(vaccineCenterQueryServiceResponseModels);
    }
}
