package com.depromeet.team5.count_user;

import com.depromeet.team5.service.UserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * TODO: 
 *  전체 사용자 수
 *  어제 가입자 수 (전체, 플랫폼별)
 *  어제 탈퇴자 수 (전체, 플랫폼별)
 *  지난주 어제 대비 가입자 수 증감 (전체, 플랫폼별)
 *  지난주 어제 대비 가입자 수 증감 (전체, 플랫폼별)
 */
@Profile("count_user")
@Configuration
public class CountUserJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private UserService userService;

    @Bean
    public Job countUserJob() {
        return jobBuilderFactory.get("countUserJob")
                .repository(jobRepository)
                .start(stepBuilderFactory.get("countUserStep")
                        .tasklet(countUserTasklet())
                        .build())
                .build();
    }

    @Bean
    public Tasklet countUserTasklet() {
        return new CountUserTasklet(userService);
    }
}
