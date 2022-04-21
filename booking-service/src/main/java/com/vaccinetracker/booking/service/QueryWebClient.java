package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.query.model.QueryWebClientRequestModel;
import com.vaccinetracker.booking.query.model.QueryWebClientResponseModel;

public interface QueryWebClient {
    QueryWebClientResponseModel getById(QueryWebClientRequestModel queryWebClientRequestModel);
}
