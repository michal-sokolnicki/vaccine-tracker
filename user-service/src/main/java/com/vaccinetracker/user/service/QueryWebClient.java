package com.vaccinetracker.user.service;

import com.vaccinetracker.user.query.model.BookingQueryWebClientResponse;
import com.vaccinetracker.user.query.model.VaccineCenterQueryWebClientResponse;

import java.util.List;

public interface QueryWebClient {

    List<BookingQueryWebClientResponse> getBookingByGovId(String govId);
    List<VaccineCenterQueryWebClientResponse> searchVaccineCenterByText(String text);
}
