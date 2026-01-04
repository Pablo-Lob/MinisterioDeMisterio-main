package org.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("=================================================");
            log.info(" JOB FINALIZADO: Todos los artefactos han sido procesados y guardados.");
            log.info("=================================================");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("☠️ EL JOB HA FALLADO. Por favor revisa los errores anteriores en el log.");
        }
    }
}