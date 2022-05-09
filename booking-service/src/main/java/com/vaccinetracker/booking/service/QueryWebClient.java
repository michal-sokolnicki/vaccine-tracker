package com.vaccinetracker.booking.service;

import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;

import java.util.List;

public interface QueryWebClient {

    BookingQueryWebClientResponse getBookingById(String govId);
}
