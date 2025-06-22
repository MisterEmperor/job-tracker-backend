package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.Job;
import com.jobtracker.jobtracker_app.domain.JobApplication;
import com.jobtracker.jobtracker_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    JobApplication findFirstByUser(User user);

    JobApplication findFirstByJob(Job job);

}
