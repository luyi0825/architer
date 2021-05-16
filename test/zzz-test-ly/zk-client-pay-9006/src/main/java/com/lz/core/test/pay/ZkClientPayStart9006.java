package com.lz.core.test.pay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZkClientPayStart9006 {
    public static void main(String[] args) {
        SpringApplication.run(ZkClientPayStart9006.class, args);
    }
}
