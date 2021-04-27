package com.lz.core.cache.common;

import com.lz.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@CustomEnableCaching(mode = AdviceMode.ASPECTJ)
public class CommonCachingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonCachingApplication.class, args);
    }

}
