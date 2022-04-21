package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.query.model.QueryWebClientRequestModel;
import com.vaccinetracker.booking.query.model.QueryWebClientResponseModel;
import com.vaccinetracker.booking.service.QueryWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class BookingQueryWebClient implements QueryWebClient {

    private final WebClient.Builder webClientBuilder;

    public BookingQueryWebClient(@Qualifier("webClientBuilder") WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public QueryWebClientResponseModel getById(QueryWebClientRequestModel queryWebClientRequestModel) {
        log.info("Querying by id: {}", queryWebClientRequestModel.getId());
        return null;
    }
}
