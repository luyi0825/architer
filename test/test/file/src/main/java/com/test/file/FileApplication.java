package com.test.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        new SpringApplication(FileApplication.class).run(args);
    }
}
