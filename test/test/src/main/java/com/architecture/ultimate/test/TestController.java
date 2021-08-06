package com.architecture.ultimate.test;

import com.architecture.ultimate.test.feign.TestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private TestFeignClient testFeignClient;

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int port;

    @GetMapping("/test")
    public String test() {
        return port + "";
    }

    @GetMapping("/testService/test")
    public String testService() {
        return port + "_testService";
    }


    @GetMapping("/testSayHello")
    public String testSayHello() {
        return restTemplate.getForObject("http://test-server/sayHello", String.class, new HashMap<>());
        //return testFeignClient.sayHello();
    }


    @GetMapping("/sayHello")
    public String sayHello() {
        return port + "sayHello";
    }


}
