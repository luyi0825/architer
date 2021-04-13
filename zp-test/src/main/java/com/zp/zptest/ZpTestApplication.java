package com.zp.zptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class ZpTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZpTestApplication.class, args);
    }

}
