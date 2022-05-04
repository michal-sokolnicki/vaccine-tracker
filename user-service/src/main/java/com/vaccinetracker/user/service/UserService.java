package com.vaccinetracker.user.service;

import com.vaccinetracker.booking.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;

import java.util.List;

public interface UserService {

    void registerUser(UserRequest userRequest);
    List<UserQueryWebClientResponse> getBookingByGovId(String govId);
}
