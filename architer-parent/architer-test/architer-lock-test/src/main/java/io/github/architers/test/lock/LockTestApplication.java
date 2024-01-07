package io.github.architers.test.lock;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LockTestApplication {
    public static void main(String[] args) {
        new SpringApplication(LockTestApplication.class).run(args);
    }
}
