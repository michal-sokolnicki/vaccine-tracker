package com.vaccinetracker.security.common;

import com.vaccinetracker.config.SecurityServiceConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Qualifier("service-audience-validator")
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final SecurityServiceConfigData securityServiceConfigData;

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(securityServiceConfigData.getCustomAudience())) {
            return OAuth2TokenValidatorResult.success();
        } else {
            OAuth2Error audienceError =
                    new OAuth2Error("invalid_token", "The required audience " +
                            securityServiceConfigData.getCustomAudience() + " is missing!",
                            null);
            return OAuth2TokenValidatorResult.failure(audienceError);
        }
    }
}
