package com.jobtracker.jobtracker_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdzunaJobResponse {
    @JsonProperty("results")
    private List<AdzunaJob> results;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("mean")
    private Double meanSalary;

    @JsonProperty("__CLASS__")
    private String responseClass;
}