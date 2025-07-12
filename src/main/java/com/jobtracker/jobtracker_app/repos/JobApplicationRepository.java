package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.model.Job;
import com.jobtracker.jobtracker_app.domain.model.JobApplication;
import com.jobtracker.jobtracker_app.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    JobApplication findFirstByUser(User user);

    JobApplication findFirstByJob(Job job);

}
