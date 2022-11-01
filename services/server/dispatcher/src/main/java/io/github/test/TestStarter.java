package io.github.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestStarter {

    public static void main(String[] args) {
        new SpringApplication(TestStarter.class).run(args);
    }
}
