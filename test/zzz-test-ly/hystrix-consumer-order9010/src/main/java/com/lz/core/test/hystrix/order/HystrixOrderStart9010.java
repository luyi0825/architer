package com.lz.core.test.hystrix.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author luyi
 * Hystrix-pay
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HystrixOrderStart9010 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixOrderStart9010.class, args);
    }
}
