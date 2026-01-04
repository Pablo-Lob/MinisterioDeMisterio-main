package org.example.config;

import org.example.domain.ArtefactoMagico;
import org.example.listener.JobNotificationListener;
import org.example.listener.StepNotificationListener; // <--- NUEVO IMPORT
import org.example.processor.ArtefactoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // 1. READER
    @Bean
    public FlatFileItemReader<ArtefactoMagico> reader() {
        return new FlatFileItemReaderBuilder<ArtefactoMagico>()
                .name("artefactoItemReader")
                .resource(new ClassPathResource("datos_magicos.csv"))
                .delimited()
                .names("id", "nombre", "nivelPeligrosidad", "estaMaldito")
                .targetType(ArtefactoMagico.class)
                .linesToSkip(1)
                .build();
    }

    // 2. WRITER
    @Bean
    public JdbcBatchItemWriter<ArtefactoMagico> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ArtefactoMagico>()
                .sql("INSERT INTO artefacto_magico (nombre, nivel_peligrosidad, estado_procesamiento) VALUES (:nombre, :nivelPeligrosidad, :estadoProcesamiento)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    // 3. STEP
    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      JdbcBatchItemWriter<ArtefactoMagico> writer,
                      ArtefactoProcessor processor,
                      StepNotificationListener stepListener) { // <--- INYECCIÓN DEL LISTENER
        return new StepBuilder("step1", jobRepository)
                .<ArtefactoMagico, ArtefactoMagico>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .listener(stepListener) // <--- REGISTRO DEL LISTENER
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .skipLimit(2)
                .skip(Exception.class)
                .build();
    }

    // 4. JOB
    @Bean
    public Job importArtefactoJob(JobRepository jobRepository,
                                  JobNotificationListener listener,
                                  Step step1) {
        return new JobBuilder("importArtefactoJob", jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}