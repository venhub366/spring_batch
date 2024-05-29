package com.batch.example.BootBatch3X.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListenerImpl implements JobExecutionListener {
    private Logger log = LoggerFactory.getLogger(JobCompletionListenerImpl.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
       log.info("job started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("job completed");
        } else {
            log.info("jon failed");
        }

    }
}
