package io.github.architers.test.lock;


import io.github.architers.context.lock.annotation.EnableLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLock
public class LockTestApplication {
    public static void main(String[] args) {
        new SpringApplication(LockTestApplication.class).run(args);
    }
}
