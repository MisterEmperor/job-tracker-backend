package com.jobtracker.jobtracker_app.repos;

import com.jobtracker.jobtracker_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
