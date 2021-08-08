package com.example.springbatchhello.batch;

import com.example.springbatchhello.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class HelloJobConfig {

    @Bean
    public Job helloWorldJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders){
        return jobBuilders.get("helloWorldJob").start(helloWorldStep(stepBuilders)).build();
    }

    private Step helloWorldStep(StepBuilderFactory stepBuilders) {
       return stepBuilders.get("helloWorldStep").<Person,String>chunk(10).reader(reader())
       .processor(processor()).writer(writer()).build();
    }

    private ItemWriter<String> writer() {
        return new FlatFileItemWriterBuilder<String>()
                .name("greetingItemWriter")
                .resource(new FileSystemResource(
                        "target/test-outputs/greetings.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();
    }

    private PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    private FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("csv/persons.csv"))
                .delimited().names(new String[] {"firstName", "lastName"})
                .targetType(Person.class).build();
    }


}
