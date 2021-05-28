package com.lz.core.test.pay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author luyi
 * <p>
 * Eureka服务端
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@EnableEurekaClient
public class EurekaClientPayStart9003 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientPayStart9003.class, args);
    }
}
