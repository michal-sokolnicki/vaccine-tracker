package com.vaccinetracker.query.service;

import com.vaccinetracker.query.model.BookingQueryRequest;
import com.vaccinetracker.query.model.BookingQueryResponse;

import java.util.List;

public interface BookingQueryService {

    BookingQueryResponse getBookingById(String id);
    List<BookingQueryResponse> getBookingByGovIdAndStatus(String govId, String status);
    List<BookingQueryResponse> getBookingHistoryByGovId(String govId);
    List<BookingQueryResponse> getBookingByDateRange(BookingQueryRequest bookingQueryRequest);
    List<BookingQueryResponse> searchByText(String text);
}
