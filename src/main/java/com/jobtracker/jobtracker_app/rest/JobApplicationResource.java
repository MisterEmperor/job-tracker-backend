package com.jobtracker.jobtracker_app.rest;

import com.jobtracker.jobtracker_app.model.JobApplicationDTO;
import com.jobtracker.jobtracker_app.service.JobApplicationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/jobApplications", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobApplicationResource {

    private final JobApplicationService jobApplicationService;

    public JobApplicationResource(final JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> getAllJobApplications() {
        return ResponseEntity.ok(jobApplicationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDTO> getJobApplication(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jobApplicationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createJobApplication(
            @RequestBody @Valid final JobApplicationDTO jobApplicationDTO) {
        final Long createdId = jobApplicationService.create(jobApplicationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateJobApplication(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final JobApplicationDTO jobApplicationDTO) {
        jobApplicationService.update(id, jobApplicationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable(name = "id") final Long id) {
        jobApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
