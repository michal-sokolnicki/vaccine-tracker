package com.vaccinetracker.booking.controller;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.entity.BookingRequestUpdate;
import com.vaccinetracker.booking.service.impl.BookingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccine/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;

    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> booking(@RequestBody final BookingRequest bookingRequest) {
        bookingService.booking(bookingRequest);
        return new ResponseEntity<>("Your reservation has been scheduled", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") final String id,
                                             @RequestBody BookingRequestUpdate bookingRequestUpdate) {
        bookingService.updateBooking(id, bookingRequestUpdate);
        return new ResponseEntity<>("Your reservation has been updated", HttpStatus.ACCEPTED);
    }
}
