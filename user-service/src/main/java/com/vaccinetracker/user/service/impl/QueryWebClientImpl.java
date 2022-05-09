package com.vaccinetracker.user.service.impl;

import com.vaccinetracker.config.QueryWebClientConfigData;
import com.vaccinetracker.user.service.QueryWebClient;
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
import org.springframework.web.util.UriComponentsBuilder;
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
    public List<BookingQueryWebClientResponse> getBookingByGovId(String govId) {
        log.info("Querying by gov id: {}", govId);
        WebClient.ResponseSpec responseSpec = getWebClient(
                queryWebClientConfigData.getQueryBookingPath().getOwnedUri() + govId);
        return responseSpec.bodyToFlux(BookingQueryWebClientResponse.class)
                .collectList()
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

    @Override
    public List<VaccineCenterQueryWebClientResponse> searchVaccineCenterByText(String text) {
        log.info("Searching vaccine center by text: {}", text);
        String uri = UriComponentsBuilder.fromPath(
                queryWebClientConfigData.getQueryVaccineCenterPath().getSearchUri())
                .queryParam("text", text)
                .build()
                .toUriString();
        WebClient.ResponseSpec responseSpec = getWebClient(uri);
        return responseSpec.bodyToFlux(VaccineCenterQueryWebClientResponse.class)
                .collectList()
                .block();
    }
}
