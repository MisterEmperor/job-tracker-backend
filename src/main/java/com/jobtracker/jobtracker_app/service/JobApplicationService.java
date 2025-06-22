package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.domain.Job;
import com.jobtracker.jobtracker_app.domain.JobApplication;
import com.jobtracker.jobtracker_app.domain.User;
import com.jobtracker.jobtracker_app.model.JobApplicationDTO;
import com.jobtracker.jobtracker_app.repos.JobApplicationRepository;
import com.jobtracker.jobtracker_app.repos.JobRepository;
import com.jobtracker.jobtracker_app.repos.UserRepository;
import com.jobtracker.jobtracker_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public JobApplicationService(final JobApplicationRepository jobApplicationRepository,
            final UserRepository userRepository, final JobRepository jobRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public List<JobApplicationDTO> findAll() {
        final List<JobApplication> jobApplications = jobApplicationRepository.findAll(Sort.by("id"));
        return jobApplications.stream()
                .map(jobApplication -> mapToDTO(jobApplication, new JobApplicationDTO()))
                .toList();
    }

    public JobApplicationDTO get(final Long id) {
        return jobApplicationRepository.findById(id)
                .map(jobApplication -> mapToDTO(jobApplication, new JobApplicationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JobApplicationDTO jobApplicationDTO) {
        final JobApplication jobApplication = new JobApplication();
        mapToEntity(jobApplicationDTO, jobApplication);
        return jobApplicationRepository.save(jobApplication).getId();
    }

    public void update(final Long id, final JobApplicationDTO jobApplicationDTO) {
        final JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jobApplicationDTO, jobApplication);
        jobApplicationRepository.save(jobApplication);
    }

    public void delete(final Long id) {
        jobApplicationRepository.deleteById(id);
    }

    private JobApplicationDTO mapToDTO(final JobApplication jobApplication,
            final JobApplicationDTO jobApplicationDTO) {
        jobApplicationDTO.setId(jobApplication.getId());
        jobApplicationDTO.setApplicationDate(jobApplication.getApplicationDate());
        jobApplicationDTO.setStatus(jobApplication.getStatus());
        jobApplicationDTO.setNotes(jobApplication.getNotes());
        jobApplicationDTO.setUser(jobApplication.getUser() == null ? null : jobApplication.getUser().getId());
        jobApplicationDTO.setJob(jobApplication.getJob() == null ? null : jobApplication.getJob().getId());
        return jobApplicationDTO;
    }

    private JobApplication mapToEntity(final JobApplicationDTO jobApplicationDTO,
            final JobApplication jobApplication) {
        jobApplication.setApplicationDate(jobApplicationDTO.getApplicationDate());
        jobApplication.setStatus(jobApplicationDTO.getStatus());
        jobApplication.setNotes(jobApplicationDTO.getNotes());
        final User user = jobApplicationDTO.getUser() == null ? null : userRepository.findById(jobApplicationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        jobApplication.setUser(user);
        final Job job = jobApplicationDTO.getJob() == null ? null : jobRepository.findById(jobApplicationDTO.getJob())
                .orElseThrow(() -> new NotFoundException("job not found"));
        jobApplication.setJob(job);
        return jobApplication;
    }

}
