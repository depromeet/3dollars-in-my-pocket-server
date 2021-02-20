package com.depromeet.team5.paprika.input;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate.query("SELECT user_id, store_name, latitude, longitude, image_url FROM paprika_store",
                    (rs, row) -> PaprikaStore.builder()
                            .userId(rs.getString(1))
                            .storeName(rs.getString(2))
                            .latitude(Double.valueOf(rs.getString(3)))
                            .longitude(Double.valueOf(rs.getString(4)))
                            .imageUrl(rs.getString(5))
                            .build()
            ).forEach(store -> log.info("Found <" + store + "> in the database."));
        }
    }
}
