package com.vaccinetracker.vaccinecenter.config;

import com.vaccinetracker.config.QueryWebClientConfigData;
import com.vaccinetracker.config.UserConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class VaccineCenterWebClientConfig {

    private final QueryWebClientConfigData.WebClient queryWebClientConfigData;
    private final UserConfigData userConfigData;

    public VaccineCenterWebClientConfig(QueryWebClientConfigData queryWebClientConfigData, UserConfigData userConfigData) {
        this.queryWebClientConfigData = queryWebClientConfigData.getWebClient();
        this.userConfigData = userConfigData;
    }

    @LoadBalanced
    @Bean("webClientBuilder")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(userConfigData.getUsername(),
                        userConfigData.getPassword()))
                .baseUrl(queryWebClientConfigData.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, queryWebClientConfigData.getContentType())
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                        .maxInMemorySize(queryWebClientConfigData.getMaxInMemorySize()));
    }

    private HttpClient getHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, queryWebClientConfigData.getConnectionTimeoutMs())
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(queryWebClientConfigData.getReadTimeoutMs(),
                                        TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(queryWebClientConfigData.getWriteTimeoutMs(),
                                        TimeUnit.MILLISECONDS)));
    }
}
