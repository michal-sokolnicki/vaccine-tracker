package com.vaccinetracker.booking.controller;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.entity.UpdateBookingRequest;
import com.vaccinetracker.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccine/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> book(@RequestBody final BookingRequest bookingRequest) {
        bookingService.book(bookingRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<String> deleteBook(@PathVariable("identifier") final String identifier) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<String> updateBook(@PathVariable("identifier") final String identifier,
                                                         @RequestBody UpdateBookingRequest updateBookingRequest) {
        return ResponseEntity.ok().build();
    }
}
