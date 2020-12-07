package com.depromeet.team5.sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("sample")
@Configuration
public class SampleJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepBuilderFactory.get("sampleStep")
                        .tasklet(sampleTasklet())
                        .build())
                .build();
    }

    @Bean
    public Tasklet sampleTasklet() {
        return new SampleTasklet();
    }
}
