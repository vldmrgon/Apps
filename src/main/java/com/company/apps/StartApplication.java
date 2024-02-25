package com.company.apps;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        log.info("Please, start by reading the README.md");
        SpringApplication.run(StartApplication.class, args);
    }
}