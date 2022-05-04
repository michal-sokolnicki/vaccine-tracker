package com.vaccinetracker.user.service;

import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;

import java.util.List;

public interface BookingQueryWebClient {

    List<UserQueryWebClientResponse> getBookingByGovId(String govId);
}
