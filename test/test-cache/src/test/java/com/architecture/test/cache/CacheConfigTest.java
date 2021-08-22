package com.architecture.test.cache;

import com.architecture.context.cache.annotation.EnableCustomCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCustomCaching(proxyTargetClass = true)
@EnableTransactionManagement
public class CacheConfigTest {
    public static void main(String[] args) {
        SpringApplication.run(CacheConfigTest.class, args);
    }
}
