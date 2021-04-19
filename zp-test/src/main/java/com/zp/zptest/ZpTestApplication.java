package com.zp.zptest;

import com.lz.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;


//@EnableCaching
@CustomEnableCaching(mode = AdviceMode.PROXY)
@SpringBootApplication
public class ZpTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZpTestApplication.class, args);
    }

}
