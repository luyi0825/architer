package io.github.architers.lock.redisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedissonLockApplication {
    public static void main(String[] args) {
        new SpringApplication(RedissonLockApplication.class).run(args);
    }
}
