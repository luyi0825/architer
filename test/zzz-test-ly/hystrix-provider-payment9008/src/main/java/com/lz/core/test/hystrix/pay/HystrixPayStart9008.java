package com.lz.core.test.hystrix.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author luyi
 * Hystrix-pay
 */
@SpringBootApplication
@EnableDiscoveryClient
//开启Hystrix
@EnableHystrix
public class HystrixPayStart9008 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixPayStart9008.class, args);
    }
}
