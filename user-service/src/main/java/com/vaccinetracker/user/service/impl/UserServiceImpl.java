package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.user.model.UserRequest;
import com.vaccinetracker.user.service.QueryWebClient;
import com.vaccinetracker.user.service.RegisterService;
import com.vaccinetracker.user.service.UserService;
import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;
import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RegisterService registerService;
    private final QueryWebClient queryWebClient;

    @Override
    public void registerUser(UserRequest userRequest) {
        registerService.registerUser(userRequest);
    }

    @Override
    public List<BookingQueryWebClientResponse> getBookingByGovId(String govId) {
        return queryWebClient.getBookingByGovId(govId);
    }

    @Override
    public List<VaccineCenterQueryWebClientResponse> searchVaccineCenterByText(String text) {
        return queryWebClient.searchVaccineCenterByText(text);
    }
}
