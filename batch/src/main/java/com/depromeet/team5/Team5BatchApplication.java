package com.depromeet.team5;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Team5BatchApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .parent(Team5DomainApplication.class)
                .child(Team5BatchApplication.class)
                .run(args);
    }
}
