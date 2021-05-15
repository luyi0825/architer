package com.lz.core.test.order;

import com.lz.core.service.response.BaseResponse;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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


    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @RequestMapping("/get/{id}")
    public BaseResponse get(@PathVariable String id) {
        return restTemplate.getForObject("/pay/get/" + id, BaseResponse.class);
    }
}
