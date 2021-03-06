package com.vaccinetracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "query-web-client")
public class QueryWebClientConfigData {

    private final WebClient webClient;
    private final Query queryBookingPath;
    private final Query queryVaccineCenterPath;

    @Data
    @Component
    public static class WebClient {
        private Integer connectionTimeoutMs;
        private Integer readTimeoutMs;
        private Integer writeTimeoutMs;
        private Integer maxInMemorySize;
        private String contentType;
        private String acceptType;
        private String baseUrl;
    }

    @Data
    @Component
    public static class Query {
        private String baseUri;
        private String ownedUri;
        private String searchUri;
    }
}
