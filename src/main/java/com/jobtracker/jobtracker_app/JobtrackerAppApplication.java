package com.jobtracker.jobtracker_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class JobtrackerAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(JobtrackerAppApplication.class, args);
    }

}
