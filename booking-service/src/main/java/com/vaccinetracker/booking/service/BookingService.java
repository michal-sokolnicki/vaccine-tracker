package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.entity.BookingRequestUpdate;

public interface BookingService {

    void booking(BookingRequest bookingRequest);
    void updateBooking(String id, BookingRequestUpdate bookingRequestUpdate);
}
