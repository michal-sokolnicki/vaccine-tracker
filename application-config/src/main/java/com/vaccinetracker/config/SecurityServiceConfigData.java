package com.vaccinetracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security-service")
public class SecurityServiceConfigData {

    private String customAudience;
}
