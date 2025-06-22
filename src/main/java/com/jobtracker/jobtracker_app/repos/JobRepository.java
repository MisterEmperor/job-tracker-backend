package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.Job;
import com.jobtracker.jobtracker_app.domain.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, Long> {

    Job findFirstBySearch(JobSearch jobSearch);

}
