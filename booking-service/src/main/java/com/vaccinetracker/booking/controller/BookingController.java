package com.vaccinetracker.booking.controller;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.booking.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vaccine/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;

    @PostMapping
    public ResponseEntity<String> booking(@RequestBody final BookingRequest bookingRequest) {
        bookingService.booking(bookingRequest);
        return new ResponseEntity<>("Your reservation has been scheduled", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") final String id,
                                             @RequestBody final BookingRequest bookingRequest) {
        bookingService.updateBooking(id, bookingRequest);
        return new ResponseEntity<>("Your reservation has been updated", HttpStatus.ACCEPTED);
    }
}
