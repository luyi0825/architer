package io.github.architers.test.valid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidTestApplication {
    public static void main(String[] args) {
        new SpringApplication(ValidTestApplication.class).run(args);
    }
}
