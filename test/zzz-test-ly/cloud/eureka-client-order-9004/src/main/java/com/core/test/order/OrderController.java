package com.core.test.order;

import com.core.service.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    public OrderController() {
        System.out.println("OrderController init");
    }


    @Autowired(required = false)
    @LoadBalanced
    private RestTemplate restTemplate;

    @GetMapping("/get/{id}")
    public BaseResponse get(@PathVariable String id) {
        //用restTemplate 调用服务
        return restTemplate.getForObject("http://EUREKA-CLIENT-PAY/pay/get/" + id, BaseResponse.class);
    }
}
