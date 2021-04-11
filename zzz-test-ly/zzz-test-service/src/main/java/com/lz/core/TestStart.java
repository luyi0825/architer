package com.lz.core;

import com.lz.core.boot.MoulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 */
@SpringBootApplication
public class TestStart {
    public static void main(String[] args) {
        Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(classes, args);
    }

}
