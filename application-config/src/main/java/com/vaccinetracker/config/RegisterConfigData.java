package com.vaccinetracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "register-config")
public class RegisterConfigData {

    String govIdAttribute;
    String personGroupPath;
    String vaccineCenterIdAttribute;
    String vaccineCenterGroupPath;
}
