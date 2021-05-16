package com.lz.core.test.order;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author luyi
 * <p>
 * Eureka客户端
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@EnableEurekaClient
public class EurekaClientOrderStart9004 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientOrderStart9004.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public IRule iRule() {
//        return new RandomRule();
//    }
}
