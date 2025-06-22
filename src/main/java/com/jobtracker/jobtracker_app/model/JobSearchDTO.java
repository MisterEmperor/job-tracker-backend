package com.jobtracker.jobtracker_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JobSearchDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String keywords;

    @Size(max = 255)
    private String location;

    @JsonProperty("isRemote")
    private Boolean isRemote;

    @NotNull
    private Long user;

}
