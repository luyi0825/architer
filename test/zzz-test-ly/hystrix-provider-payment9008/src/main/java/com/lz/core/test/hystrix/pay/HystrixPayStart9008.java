package com.lz.core.test.hystrix.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 * Hystrix-pay
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HystrixPayStart9008 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixPayStart9008.class, args);
    }
}
