package com.depromeet.team5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team5Application {

    public static void main(String[] args) {
        SpringApplication.run(Team5Application.class, args);
    }

}
