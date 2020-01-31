package com.depromeet.team5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication( exclude = {ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class,
        MessagingAutoConfiguration.class})
@EnableJpaAuditing
public class Team5Application {

    public static void main(String[] args) {
        SpringApplication.run(Team5Application.class, args);
    }

}
