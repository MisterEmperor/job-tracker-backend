package com.jobtracker.jobtracker_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JobApplicationDTO {

    private Long id;

    @NotNull
    private LocalDate applicationDate;

    @NotNull
    @Size(max = 255)
    private String status;

    private String notes;

    @NotNull
    private Long user;

    @NotNull
    private Long job;

}
