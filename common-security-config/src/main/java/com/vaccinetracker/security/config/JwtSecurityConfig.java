package com.vaccinetracker.security.config;

import com.vaccinetracker.security.common.UserDetailsService;
import com.vaccinetracker.security.common.UserJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    private final OAuth2TokenValidator<Jwt> audienceValidator;

    public JwtSecurityConfig(UserDetailsService userDetailsService,
                          OAuth2ResourceServerProperties oAuth2ResourceServerProperties,
                          @Qualifier("service-audience-validator") OAuth2TokenValidator<Jwt> audienceValidator) {
        this.userDetailsService = userDetailsService;
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        this.audienceValidator = audienceValidator;
    }

    @Bean
    @Qualifier("user-jwt-converter")
    public Converter<Jwt, AbstractAuthenticationToken> userJwtConverter() {
        return new UserJwtConverter(userDetailsService);
    }

    @Bean
    @Qualifier("jwt-decoder")
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(
                oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(
                oAuth2ResourceServerProperties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }
}
