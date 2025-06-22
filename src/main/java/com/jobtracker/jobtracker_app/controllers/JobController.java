package com.jobtracker.jobtracker_app.controllers;

import com.jobtracker.jobtracker_app.dto.AdzunaJob;
import com.jobtracker.jobtracker_app.service.AdzunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Tag(name = "Job Search", description = "API for searching job listings")
public class JobController {

    private final AdzunaService adzunaService;

    @Operation(summary = "Search jobs via Adzuna API", description = "Fetches job listings from Adzuna based on search criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs", content = @Content(schema = @Schema(implementation = AdzunaJob.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided"),
            @ApiResponse(responseCode = "500", description = "Error fetching from Adzuna API")
    })
    @GetMapping("/adzuna")
    public List<AdzunaJob> getAdzunaJobs(
            @RequestParam String query,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer maxResults) {

        return adzunaService.fetchJobs(
                country != null ? country : "gb",
                query,
                maxResults != null ? maxResults : 10);
    }
}