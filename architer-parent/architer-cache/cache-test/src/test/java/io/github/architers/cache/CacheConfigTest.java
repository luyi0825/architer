package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableArchiterCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableArchiterCaching(proxyTargetClass = true)
@EnableTransactionManagement
public class CacheConfigTest {
    public static void main(String[] args) {
        SpringApplication.run(CacheConfigTest.class, args);
    }
}
