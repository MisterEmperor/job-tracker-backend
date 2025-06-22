package com.jobtracker.jobtracker_app.service;

import com.jobtracker.jobtracker_app.domain.Job;
import com.jobtracker.jobtracker_app.domain.JobSearch;
import com.jobtracker.jobtracker_app.domain.User;
import com.jobtracker.jobtracker_app.model.JobSearchDTO;
import com.jobtracker.jobtracker_app.repos.JobRepository;
import com.jobtracker.jobtracker_app.repos.JobSearchRepository;
import com.jobtracker.jobtracker_app.repos.UserRepository;
import com.jobtracker.jobtracker_app.util.NotFoundException;
import com.jobtracker.jobtracker_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class JobSearchService {

    private final JobSearchRepository jobSearchRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public JobSearchService(final JobSearchRepository jobSearchRepository,
            final UserRepository userRepository, final JobRepository jobRepository) {
        this.jobSearchRepository = jobSearchRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public List<JobSearchDTO> findAll() {
        final List<JobSearch> jobSearches = jobSearchRepository.findAll(Sort.by("id"));
        return jobSearches.stream()
                .map(jobSearch -> mapToDTO(jobSearch, new JobSearchDTO()))
                .toList();
    }

    public JobSearchDTO get(final Long id) {
        return jobSearchRepository.findById(id)
                .map(jobSearch -> mapToDTO(jobSearch, new JobSearchDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JobSearchDTO jobSearchDTO) {
        final JobSearch jobSearch = new JobSearch();
        mapToEntity(jobSearchDTO, jobSearch);
        return jobSearchRepository.save(jobSearch).getId();
    }

    public void update(final Long id, final JobSearchDTO jobSearchDTO) {
        final JobSearch jobSearch = jobSearchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jobSearchDTO, jobSearch);
        jobSearchRepository.save(jobSearch);
    }

    public void delete(final Long id) {
        jobSearchRepository.deleteById(id);
    }

    private JobSearchDTO mapToDTO(final JobSearch jobSearch, final JobSearchDTO jobSearchDTO) {
        jobSearchDTO.setId(jobSearch.getId());
        jobSearchDTO.setKeywords(jobSearch.getKeywords());
        jobSearchDTO.setLocation(jobSearch.getLocation());
        jobSearchDTO.setIsRemote(jobSearch.getIsRemote());
        jobSearchDTO.setUser(jobSearch.getUser() == null ? null : jobSearch.getUser().getId());
        return jobSearchDTO;
    }

    private JobSearch mapToEntity(final JobSearchDTO jobSearchDTO, final JobSearch jobSearch) {
        jobSearch.setKeywords(jobSearchDTO.getKeywords());
        jobSearch.setLocation(jobSearchDTO.getLocation());
        jobSearch.setIsRemote(jobSearchDTO.getIsRemote());
        final User user = jobSearchDTO.getUser() == null ? null : userRepository.findById(jobSearchDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        jobSearch.setUser(user);
        return jobSearch;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final JobSearch jobSearch = jobSearchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Job searchJob = jobRepository.findFirstBySearch(jobSearch);
        if (searchJob != null) {
            referencedWarning.setKey("jobSearch.job.search.referenced");
            referencedWarning.addParam(searchJob.getId());
            return referencedWarning;
        }
        return null;
    }

}
