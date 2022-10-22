package io.github.architers.starter.web.validexception;

import io.github.architers.starter.web.WebMvcAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "io.github.architers.starter.web")
@Import(WebMvcAutoConfiguration.class)
public class ExceptionTestConfig {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionTestConfig.class, args);
    }


}
