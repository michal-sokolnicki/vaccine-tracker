package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.model.BookingRequest;

public interface BookingService {

    void booking(BookingRequest bookingRequest);
    void updateBooking(String id, BookingRequest bookingRequest);
}
