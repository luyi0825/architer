package com.architecture.ultimate.starter.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.architecture.ultimate.starter.web")
public class ExceptionTestConfig {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionTestConfig.class, args);
    }
}
