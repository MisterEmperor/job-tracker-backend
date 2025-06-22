package com.jobtracker.jobtracker_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JobDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String company;

    @Size(max = 255)
    private String location;

    private String description;

    @NotNull
    @Size(max = 500)
    private String url;

    @NotNull
    @Size(max = 50)
    private String source;

    @Size(max = 255)
    private String externalId;

    private Long search;

}
