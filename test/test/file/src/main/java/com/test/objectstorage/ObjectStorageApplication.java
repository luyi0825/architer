package com.test.objectstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObjectStorageApplication {

    public static void main(String[] args) {
        new SpringApplication(ObjectStorageApplication.class).run(args);
    }
}