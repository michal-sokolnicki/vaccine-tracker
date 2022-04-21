package com.vaccinetracker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-config")
public class ElasticQueryConfigData {

    private List<String> bookingFields;
    private String govIdField;
    private String statusField;
    private List<String> vaccineCenterFields;
}
