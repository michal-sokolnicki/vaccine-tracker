package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.service.QueryWebClient;
import com.vaccinetracker.config.QueryWebClientConfigData;
import com.vaccinetracker.webclient.query.exception.QueryWebClientException;
import com.vaccinetracker.webclient.query.model.BookingQueryWebClientResponse;
import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class QueryWebClientImpl implements QueryWebClient {

    private final WebClient.Builder webClientBuilder;
    private final QueryWebClientConfigData queryWebClientConfigData;

    public QueryWebClientImpl(@Qualifier("web-client-builder") WebClient.Builder webClientBuilder,
                              QueryWebClientConfigData queryWebClientConfigData) {
        this.webClientBuilder = webClientBuilder;
        this.queryWebClientConfigData = queryWebClientConfigData;
    }

    @Override
    public BookingQueryWebClientResponse getBookingById(String id) {
        log.info("Querying by id: {}", id);
        WebClient.ResponseSpec responseSpec = getWebClient(
                queryWebClientConfigData.getQueryBookingPath().getBaseUri() + id);
        return responseSpec.bodyToMono(BookingQueryWebClientResponse.class)
                .block();
    }

    private WebClient.ResponseSpec getWebClient(String uri) {
        return webClientBuilder.build()
                .method(HttpMethod.GET)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new BadCredentialsException("Not authenticated!")))
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> Mono.just(new QueryWebClientException(clientResponse.statusCode()
                                .getReasonPhrase())))
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> Mono.just(new Exception(clientResponse.statusCode().getReasonPhrase())));
    }
}
