package com.master4uall.spring.batch.writer;

import com.master4uall.spring.data.entity.Person;
import com.master4uall.spring.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class PersonItemWriter implements ItemWriter<Person> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonItemWriter.class);

    @Autowired
    private PersonRepository personRepository;

    private RepositoryItemWriter<Person> writer;

    @PostConstruct
    private void init() {
        writer = new
                RepositoryItemWriterBuilder<Person>()
                .methodName("save")
                .repository(personRepository)
                .build();

    }

    @Override
    public void write(List<? extends Person> list) throws Exception {
        LOGGER.debug("List received for writing {}", String.valueOf(list));
        writer.write(list);
    }

}
