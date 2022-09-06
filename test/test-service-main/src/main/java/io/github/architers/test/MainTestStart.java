package io.github.architers.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class MainTestStart {
    public static void main(String[] args) {
        // Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(MainTestStart.class, args);
    }
}
