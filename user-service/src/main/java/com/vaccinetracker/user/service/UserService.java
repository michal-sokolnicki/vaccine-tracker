package com.vaccinetracker.user.service;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;

import java.util.List;

public interface UserService {

    void registerUser(UserRequest userRequest);
    List<UserQueryWebClientResponse> getBookingByGovId(String govId);
}
