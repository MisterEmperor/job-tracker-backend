package com.jobtracker.jobtracker_app.rest;

import com.jobtracker.jobtracker_app.model.JobDTO;
import com.jobtracker.jobtracker_app.service.JobService;
import com.jobtracker.jobtracker_app.util.ReferencedException;
import com.jobtracker.jobtracker_app.util.ReferencedWarning;
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
@RequestMapping(value = "/api/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobResource {

    private final JobService jobService;

    public JobResource(final JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jobService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createJob(@RequestBody @Valid final JobDTO jobDTO) {
        final Long createdId = jobService.create(jobDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateJob(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final JobDTO jobDTO) {
        jobService.update(id, jobDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteJob(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = jobService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
