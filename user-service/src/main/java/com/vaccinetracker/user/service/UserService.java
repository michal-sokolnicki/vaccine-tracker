package com.vaccinetracker.user.service;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;
import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;

import java.util.List;

public interface UserService {

    void registerUser(UserRequest userRequest);
    List<BookingQueryWebClientResponse> getBookingByGovId(String govId);
    List<VaccineCenterQueryWebClientResponse> searchVaccineCenterByText(String text);
}
