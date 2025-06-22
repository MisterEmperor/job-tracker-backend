package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.domain.Job;
import com.jobtracker.jobtracker_app.domain.JobApplication;
import com.jobtracker.jobtracker_app.domain.JobSearch;
import com.jobtracker.jobtracker_app.model.JobDTO;
import com.jobtracker.jobtracker_app.repos.JobApplicationRepository;
import com.jobtracker.jobtracker_app.repos.JobRepository;
import com.jobtracker.jobtracker_app.repos.JobSearchRepository;
import com.jobtracker.jobtracker_app.util.NotFoundException;
import com.jobtracker.jobtracker_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobSearchRepository jobSearchRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public JobService(final JobRepository jobRepository,
            final JobSearchRepository jobSearchRepository,
            final JobApplicationRepository jobApplicationRepository) {
        this.jobRepository = jobRepository;
        this.jobSearchRepository = jobSearchRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobDTO> findAll() {
        final List<Job> jobs = jobRepository.findAll(Sort.by("id"));
        return jobs.stream()
                .map(job -> mapToDTO(job, new JobDTO()))
                .toList();
    }

    public JobDTO get(final Long id) {
        return jobRepository.findById(id)
                .map(job -> mapToDTO(job, new JobDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JobDTO jobDTO) {
        final Job job = new Job();
        mapToEntity(jobDTO, job);
        return jobRepository.save(job).getId();
    }

    public void update(final Long id, final JobDTO jobDTO) {
        final Job job = jobRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jobDTO, job);
        jobRepository.save(job);
    }

    public void delete(final Long id) {
        jobRepository.deleteById(id);
    }

    private JobDTO mapToDTO(final Job job, final JobDTO jobDTO) {
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setCompany(job.getCompany());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setUrl(job.getUrl());
        jobDTO.setSource(job.getSource());
        jobDTO.setExternalId(job.getExternalId());
        jobDTO.setSearch(job.getSearch() == null ? null : job.getSearch().getId());
        return jobDTO;
    }

    private Job mapToEntity(final JobDTO jobDTO, final Job job) {
        job.setTitle(jobDTO.getTitle());
        job.setCompany(jobDTO.getCompany());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setUrl(jobDTO.getUrl());
        job.setSource(jobDTO.getSource());
        job.setExternalId(jobDTO.getExternalId());
        final JobSearch search = jobDTO.getSearch() == null ? null : jobSearchRepository.findById(jobDTO.getSearch())
                .orElseThrow(() -> new NotFoundException("search not found"));
        job.setSearch(search);
        return job;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Job job = jobRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final JobApplication jobJobApplication = jobApplicationRepository.findFirstByJob(job);
        if (jobJobApplication != null) {
            referencedWarning.setKey("job.jobApplication.job.referenced");
            referencedWarning.addParam(jobJobApplication.getId());
            return referencedWarning;
        }
        return null;
    }

}
