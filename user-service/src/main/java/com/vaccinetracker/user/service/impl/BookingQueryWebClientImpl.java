package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.config.QueryWebClientConfigData;
import com.vaccinetracker.user.query.exception.QueryWebClientException;
import com.vaccinetracker.user.query.model.UserQueryWebClientResponse;
import com.vaccinetracker.user.service.BookingQueryWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BookingQueryWebClientImpl implements BookingQueryWebClient {

    private final WebClient.Builder webClientBuilder;
    private final QueryWebClientConfigData queryWebClientConfigData;

    public BookingQueryWebClientImpl(@Qualifier("web-client-builder") WebClient.Builder clientBuilder,
                                     QueryWebClientConfigData queryWebClientConfigData) {
        this.webClientBuilder = clientBuilder;
        this.queryWebClientConfigData = queryWebClientConfigData;
    }

    @Override
    public List<UserQueryWebClientResponse> getBookingByGovId(String govId) {
        log.info("Querying by govId: {}", govId);
        WebClient.ResponseSpec responseSpec = getWebClient(
                queryWebClientConfigData.getQueryBookingPath().getOwnedUri() + govId);
        return responseSpec.bodyToFlux(UserQueryWebClientResponse.class)
                .collectList()
                .blockOptional()
                .orElseGet(Collections::emptyList);
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
