package com.depromeet.team5.paprika.input;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Profile("paprika_input")
@Configuration
public class PaprikaInputConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job inputPaprikaStoreJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("input_paprika_store_job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<PaprikaStore> writer) {
        return stepBuilderFactory.get("step1")
                .<PaprikaStoreItem, PaprikaStore> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<PaprikaStoreItem> reader() {
        return new FlatFileItemReaderBuilder<PaprikaStoreItem>()
                .name("paprikaStoreItemReader")
                .resource(new ClassPathResource("store.csv"))
                .delimited()
                .names("userId", "fileName", "location", "createdAt")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<PaprikaStoreItem>() {{
                    setTargetType(PaprikaStoreItem.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<PaprikaStoreItem, PaprikaStore> processor() {
        return new PaprikaStoreItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<PaprikaStore> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PaprikaStore>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO paprika_store (user_id, store_name, latitude, longitude, image_url, created_at) " +
                        "VALUES (:userId, :storeName, :latitude, :longitude, :imageUrl, :createdAt)")
                .dataSource(dataSource)
                .build();
    }
}
