package com.master4uall.spring.batch.listener;

import com.master4uall.spring.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("Job Status {}", jobExecution.getStatus());
        personRepository
                .findAll()
                .forEach(person -> LOGGER.info("Found {} in database", person));
    }


    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("Job Status {}", jobExecution.getStatus());
        personRepository.deleteAll();
    }
}
