package org.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepNotificationListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(StepNotificationListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("▶️ INICIANDO STEP: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("⏹️ FIN DEL STEP: Leídos [{}], Escritos [{}], Saltados [{}]",
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getSkipCount());

        if (stepExecution.getSkipCount() > 0) {
            log.warn("⚠️ ATENCIÓN: Se detectaron {} registros con errores durante el paso.", stepExecution.getSkipCount());
        }

        return stepExecution.getExitStatus();
    }
}