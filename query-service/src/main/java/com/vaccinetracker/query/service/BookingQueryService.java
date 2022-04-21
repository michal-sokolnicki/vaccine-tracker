package com.vaccinetracker.query.service;

import com.vaccinetracker.query.model.BookingQueryServiceResponseModel;

import java.util.List;

public interface BookingQueryService {

    BookingQueryServiceResponseModel getBookingById(String id);
    List<BookingQueryServiceResponseModel> getBookingByGovIdAndStatus(String govId, String status);
    List<BookingQueryServiceResponseModel> getBookingHistoryByGovId(String govId);
    List<BookingQueryServiceResponseModel> searchByText(String text);
}
