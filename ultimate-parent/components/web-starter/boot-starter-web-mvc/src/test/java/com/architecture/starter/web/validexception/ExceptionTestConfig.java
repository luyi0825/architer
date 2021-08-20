package com.architecture.starter.web.validexception;

import com.architecture.starter.web.StarterWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.architecture.starter.web")
@Import(StarterWebConfig.class)
public class ExceptionTestConfig {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionTestConfig.class, args);
    }


}
