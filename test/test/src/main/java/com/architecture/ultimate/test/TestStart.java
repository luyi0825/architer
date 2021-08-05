package com.architecture.ultimate.test;


;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/")
public class TestStart {

    public static void main(String[] args) {
        // Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(TestStart.class, args);
    }


}
