package com.jobtracker.jobtracker_app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AdzunaConfig {

    @Bean
    @ConfigurationProperties(prefix = "adzuna.api")
    public AdzunaProperties adzunaProperties() {
        return new AdzunaProperties();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Data
    @ConfigurationProperties(prefix = "adzuna.api")
    public static class AdzunaProperties {
        private String baseUrl;
        private String id;
        private String key;
    }
}