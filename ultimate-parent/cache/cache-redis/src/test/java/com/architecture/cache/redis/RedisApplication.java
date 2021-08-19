package com.architecture.cache.redis;

import com.architecture.ultimate.module.common.cache.annotation.EnableCustomCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@EnableCustomCaching(mode = AdviceMode.PROXY)
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
