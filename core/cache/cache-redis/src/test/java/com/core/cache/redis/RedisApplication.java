package com.core.cache.redis;

import com.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@CustomEnableCaching(mode = AdviceMode.PROXY)
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}