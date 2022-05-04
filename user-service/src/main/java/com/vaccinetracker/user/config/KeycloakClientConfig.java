package com.vaccinetracker.user.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.authorization-grant-type}")
    private String grantType;

    @Bean
    @Qualifier("keycloak-client")
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .serverUrl(serverUrl)
                .grantType(grantType)
                .build();
    }
}
