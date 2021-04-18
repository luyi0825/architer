package com.lz.core.test;


import com.lz.core.boot.MoulesBuilder;
import com.lz.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

/**
 * @author luyi
 */
@SpringBootApplication
@CustomEnableCaching(mode = AdviceMode.PROXY)
public class TestStart {
    public static void main(String[] args) {
        Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(classes, args);
    }

}
