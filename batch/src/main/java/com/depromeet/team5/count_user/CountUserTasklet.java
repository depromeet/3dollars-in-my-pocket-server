package com.depromeet.team5.count_user;

import com.depromeet.team5.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CountUserTasklet implements Tasklet {
    private final UserService userService;

    public CountUserTasklet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
        CountUserStrategy countUserStrategy = this.resolve(jobParameters);
        log.info("jobParameters: {}", jobParameters);
        long countOfUsers = countUserStrategy.countUsers();
        log.info("countOfUsers: {}", countOfUsers);
        return RepeatStatus.FINISHED;
    }

    private CountUserStrategy resolve(Map<String, Object> jobParameters) {
        String name = (String) jobParameters.get("strategy");
        LocalDate targetDate = Optional.ofNullable(jobParameters.get("targetDate"))
                .map(it -> (String) it)
                .map(it -> LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyyMMdd")))
                .orElseGet(() -> LocalDate.now().minusDays(1L));
        switch (CountUserStrategyType.from(name)) {
            case DAILY:
                return new DailyUserStrategy(userService, targetDate);
            case ALL:
                return new AllUserStrategy(userService, targetDate);
            default:
                throw new IllegalArgumentException(MessageFormat.format("{0} is not supported type", name));
        }
    }
}
