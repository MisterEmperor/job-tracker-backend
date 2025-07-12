package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.model.JobSearch;
import com.jobtracker.jobtracker_app.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobSearchRepository extends JpaRepository<JobSearch, Long> {

    JobSearch findFirstByUser(User user);

}
