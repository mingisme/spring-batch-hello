package com.example.springbatchhello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBatchHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchHelloApplication.class, args);
    }

}
