package com.vaccinetracker.webclient.config;

import com.vaccinetracker.config.QueryWebClientConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class QueryServiceWebClientConfig {

    private final QueryWebClientConfigData.WebClient queryWebClientConfigData;

    @Value("${security.default-client-registration-id}")
    private String defaultClientRegistrationId;

    public QueryServiceWebClientConfig(QueryWebClientConfigData queryWebClientConfigData) {
        this.queryWebClientConfigData = queryWebClientConfigData.getWebClient();
    }

    @LoadBalanced
    @Bean("web-client-builder")
    public WebClient.Builder webClientBuilder(ClientRegistrationRepository clientRegistrationRepository,
                                              OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
                        oAuth2AuthorizedClientRepository);
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        oauth2.setDefaultClientRegistrationId(defaultClientRegistrationId);
        return WebClient.builder()
                .baseUrl(queryWebClientConfigData.getBaseUrl())
                .filter(oauth2)
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
