package com.jobtracker.jobtracker_app.rest;

import com.jobtracker.jobtracker_app.model.JobSearchDTO;
import com.jobtracker.jobtracker_app.service.JobSearchService;
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
@RequestMapping(value = "/api/jobSearches", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobSearchResource {

    private final JobSearchService jobSearchService;

    public JobSearchResource(final JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @GetMapping
    public ResponseEntity<List<JobSearchDTO>> getAllJobSearches() {
        return ResponseEntity.ok(jobSearchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSearchDTO> getJobSearch(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jobSearchService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createJobSearch(
            @RequestBody @Valid final JobSearchDTO jobSearchDTO) {
        final Long createdId = jobSearchService.create(jobSearchDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateJobSearch(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final JobSearchDTO jobSearchDTO) {
        jobSearchService.update(id, jobSearchDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteJobSearch(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = jobSearchService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        jobSearchService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
