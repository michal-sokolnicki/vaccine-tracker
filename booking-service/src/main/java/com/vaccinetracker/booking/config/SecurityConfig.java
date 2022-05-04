package com.vaccinetracker.booking.config;

import com.vaccinetracker.security.common.UserDetailsService;
import com.vaccinetracker.security.common.UserJwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final OAuth2ResourceServerProperties oAuth2ResourceServerProperties;
    private final OAuth2TokenValidator<Jwt> audienceValidator;

    public SecurityConfig(UserDetailsService userDetailsService,
                          OAuth2ResourceServerProperties oAuth2ResourceServerProperties,
                          @Qualifier("service-audience-validator") OAuth2TokenValidator<Jwt> audienceValidator) {
        this.userDetailsService = userDetailsService;
        this.oAuth2ResourceServerProperties = oAuth2ResourceServerProperties;
        this.audienceValidator = audienceValidator;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(userJwtConverter());
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> userJwtConverter() {
        return new UserJwtConverter(userDetailsService);
    }

    @Bean
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
