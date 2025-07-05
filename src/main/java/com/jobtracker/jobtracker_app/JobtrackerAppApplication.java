package com.jobtracker.jobtracker_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
public class JobtrackerAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(JobtrackerAppApplication.class, args);
    }

}
