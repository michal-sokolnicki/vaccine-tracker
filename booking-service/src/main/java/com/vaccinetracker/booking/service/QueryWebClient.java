package com.vaccinetracker.booking.service;

import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;

public interface QueryWebClient {

    BookingQueryWebClientResponse getBookingById(String govId);
}
