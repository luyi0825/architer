package com.architecture.cache.common;

import com.architecture.ultimate.module.common.cache.annotation.EnableCustomCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@EnableCustomCaching(mode = AdviceMode.ASPECTJ)
public class CommonCachingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonCachingApplication.class, args);
    }

}
