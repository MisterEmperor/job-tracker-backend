package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.model.Job;
import com.jobtracker.jobtracker_app.domain.model.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, Long> {

    Job findFirstBySearch(JobSearch jobSearch);

}
