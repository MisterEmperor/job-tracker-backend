package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.dto.AdzunaJob;
import com.jobtracker.jobtracker_app.dto.AdzunaJobResponse;
import com.jobtracker.jobtracker_app.config.AdzunaConfig.AdzunaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdzunaService {
    private static final String API_PATH = "/v1/api/jobs/{country}/search/1";
    private final RestTemplate restTemplate;
    private final AdzunaProperties properties;

    public List<AdzunaJob> fetchJobs(String countryCode, String query, int maxResults) {
        return buildUrl(countryCode, query, maxResults)
                .flatMap(this::callAdzunaApi)
                .map(AdzunaJobResponse::getResults)
                .orElse(Collections.emptyList());
    }

    private Optional<String> buildUrl(String countryCode, String query, int maxResults) {
        try {
            String url = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("api.adzuna.com")
                    .path("/v1/api/jobs/{country}/search/1")
                    .queryParam("app_id", properties.getId())
                    .queryParam("app_key", properties.getKey())
                    .queryParam("what", query)
                    .queryParam("results_per_page", maxResults)
                    .build(countryCode.toLowerCase())
                    .toString();

            log.info("Final Adzuna URL: {}", url);
            return Optional.of(url);
        } catch (Exception e) {
            log.error("URL building failed", e);
            return Optional.empty();
        }
    }

    private Optional<AdzunaJobResponse> callAdzunaApi(String url) {
        try {
            AdzunaJobResponse response = restTemplate.getForObject(url, AdzunaJobResponse.class);
            log.info("Fetched {} jobs from Adzuna",
                    response != null ? response.getResults().size() : 0);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            log.error("Adzuna API request failed", e);
            return Optional.empty();
        }
    }
}