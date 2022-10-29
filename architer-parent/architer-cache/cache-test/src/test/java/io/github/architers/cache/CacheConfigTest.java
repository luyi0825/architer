package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableArchiterCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableArchiterCaching(proxyTargetClass = true)
public class CacheConfigTest {
    public static void main(String[] args) {
        SpringApplication.run(CacheConfigTest.class, args);
    }
}
