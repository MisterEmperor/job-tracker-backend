package com.jobtracker.jobtracker_app.api.controllers;

import com.jobtracker.jobtracker_app.api.dtos.adzuna.AdzunaJob;
import com.jobtracker.jobtracker_app.api.dtos.job.JobDTO; // NEW: Import JobDTO
import com.jobtracker.jobtracker_app.domain.service.AdzunaService;
import com.jobtracker.jobtracker_app.domain.service.JobService; // NEW: Import JobService
import com.jobtracker.jobtracker_app.util.ReferencedException; // NEW: Import for delete method error handling
import com.jobtracker.jobtracker_app.util.ReferencedWarning;    // NEW: Import for delete method error handling

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Ensure this is imported for consistency
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated // Keep this if you have class-level validation needs. Otherwise, you can remove it.
@RestController
@RequestMapping(value = "/api/jobs", produces = MediaType.APPLICATION_JSON_VALUE) // Use MediaType for consistency
@RequiredArgsConstructor
@Tag(name = "Job Management & Search", description = "API for managing internal job listings and searching external jobs")
public class JobController {

    // Inject both services
    private final AdzunaService adzunaService;
    private final JobService jobService;

    // --- Original Adzuna Job Search Endpoint ---
    @Operation(summary = "Search jobs via Adzuna API", description = "Fetches job listings from Adzuna based on search criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs", content = @Content(schema = @Schema(implementation = AdzunaJob.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided"),
            @ApiResponse(responseCode = "500", description = "Error fetching from Adzuna API")
    })
    @GetMapping("/adzuna") // Full path: /api/jobs/adzuna
    public List<AdzunaJob> getAdzunaJobs(
            @RequestParam String query,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer maxResults) {

        return adzunaService.fetchJobs(
                country != null ? country : "gb",
                query,
                maxResults != null ? maxResults : 10);
    }

    // --- Merged Internal Job Management Endpoints (from JobResource.java) ---

    @Operation(summary = "Get all internal job listings", description = "Retrieves a list of all jobs stored in the application's database")
    @GetMapping // Full path: /api/jobs
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @Operation(summary = "Get a job listing by ID", description = "Retrieves a specific job listing from the database by its unique ID")
    @GetMapping("/{id}") // Full path: /api/jobs/{id}
    public ResponseEntity<JobDTO> getJob(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jobService.get(id));
    }

    @Operation(summary = "Create a new job listing", description = "Adds a new job listing to the application's database")
    @PostMapping // Full path: /api/jobs
    @ApiResponse(responseCode = "201", description = "Job created successfully", content = @Content(schema = @Schema(implementation = Long.class)))
    @ApiResponse(responseCode = "400", description = "Invalid job data provided")
    public ResponseEntity<Long> createJob(@RequestBody @Valid final JobDTO jobDTO) {
        final Long createdId = jobService.create(jobDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing job listing", description = "Modifies an existing job listing in the database by its ID")
    @PutMapping("/{id}") // Full path: /api/jobs/{id}
    @ApiResponse(responseCode = "200", description = "Job updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid job data provided")
    @ApiResponse(responseCode = "404", description = "Job not found")
    public ResponseEntity<Long> updateJob(@PathVariable(name = "id") final Long id,
                                          @RequestBody @Valid final JobDTO jobDTO) {
        jobService.update(id, jobDTO);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Delete a job listing", description = "Removes a job listing from the database by its ID")
    @DeleteMapping("/{id}") // Full path: /api/jobs/{id}
    @ApiResponse(responseCode = "204", description = "Job deleted successfully (No Content)")
    @ApiResponse(responseCode = "404", description = "Job not found")
    @ApiResponse(responseCode = "409", description = "Conflict, job is referenced by other entities") // For ReferencedException
    public ResponseEntity<Void> deleteJob(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = jobService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}