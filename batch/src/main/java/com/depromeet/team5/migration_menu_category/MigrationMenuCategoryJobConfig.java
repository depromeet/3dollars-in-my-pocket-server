package com.depromeet.team5.migration_menu_category;

import com.depromeet.team5.service.UserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("migration_menu_category")
@Configuration
public class MigrationMenuCategoryJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private MigrationMenuCategoryTasklet migrationMenuCategoryTasklet;

    @Bean
    public Job migrateMenuCategories() {
        return jobBuilderFactory.get("migrationMenuCategory")
                                .repository(jobRepository)
                                .start(stepBuilderFactory.get("migrationMenuCategoryStep")
                                                         .tasklet(migrationMenuCategoryTasklet)
                                                         .build())
                                .build();
    }
}
