package com.vaccinetracker.vaccinecenter.service.impl;

import com.vaccinetracker.config.QueryWebClientConfigData;
import com.vaccinetracker.vaccinecenter.query.exception.QueryWebClientException;
import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;
import com.vaccinetracker.vaccinecenter.service.QueryWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class QueryWebClientImpl implements QueryWebClient {

    private final WebClient.Builder webClientBuilder;
    private final QueryWebClientConfigData queryWebClientConfigData;

    public QueryWebClientImpl(@Qualifier("web-client-builder") WebClient.Builder clientBuilder,
                              QueryWebClientConfigData queryWebClientConfigData) {
        this.webClientBuilder = clientBuilder;
        this.queryWebClientConfigData = queryWebClientConfigData;
    }

    @Override
    public VaccineCenterQueryWebClientResponse getVaccineCenterById(String id) {
        log.info("Querying by id: {}", id);
        WebClient.ResponseSpec responseSpec = getWebClient(
                queryWebClientConfigData.getQueryVaccineCenterPath().getBaseUri() + id);
        return responseSpec.bodyToMono(VaccineCenterQueryWebClientResponse.class)
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
