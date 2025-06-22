package com.jobtracker.jobtracker_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdzunaJob {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("salary_min")
    private Double salaryMin;

    @JsonProperty("salary_max")
    private Double salaryMax;

    @JsonProperty("salary_is_predicted")
    private String salaryIsPredicted;

    @JsonProperty("contract_type")
    private String contractType;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    @JsonProperty("adref")
    private String adRef;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("company")
    private Company company;

    @JsonProperty("location")
    private JobLocation location;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        @JsonProperty("display_name")
        private String displayName;

        @JsonProperty("__CLASS__")
        private String companyClass;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JobLocation {
        @JsonProperty("display_name")
        private String displayName;

        @JsonProperty("area")
        private List<String> area;
    }
}