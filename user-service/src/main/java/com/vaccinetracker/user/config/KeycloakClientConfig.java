package com.vaccinetracker.user.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
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

    @Bean
    @Qualifier("realm-resource-client")
    public RealmResource realmResource() {
        return KeycloakBuilder.builder()
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .serverUrl(serverUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build()
                .realm(realm);
    }
}
