package com.master4uall.spring.batch.listener;

import com.master4uall.spring.batch.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            LOGGER.info("Job completed");
            RowMapper<Person> mapper = new RowMapper<Person>() {

                @Override
                public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new Person(resultSet.getString(1),resultSet.getString(2));
                }
            };

            jdbcTemplate.query("SELECT first_name, last_name FROM people",
                    mapper
                    ).forEach(person ->  LOGGER.info("Found {} in database", person));
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }


}
