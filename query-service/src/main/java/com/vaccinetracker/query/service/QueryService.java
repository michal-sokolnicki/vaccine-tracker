package com.vaccinetracker.query.service;

import com.vaccinetracker.query.model.QueryServiceResponseModel;

import java.util.List;

public interface QueryService {

    QueryServiceResponseModel getBookingById(String id);
    List<QueryServiceResponseModel> getBookingByGovIdAndStatus(String govId, String status);
    List<QueryServiceResponseModel> getBookingHistoryByGovId(String govId);
    List<QueryServiceResponseModel> searchByText(String text);
}
