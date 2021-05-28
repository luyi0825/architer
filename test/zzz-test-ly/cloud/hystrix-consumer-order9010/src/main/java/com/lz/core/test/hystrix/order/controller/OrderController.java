package com.lz.core.test.hystrix.order.controller;


import com.lz.core.test.hystrix.order.feign.PayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PayClient payClient;

    @RequestMapping("/hystrix/ok/{id}")
    public String getPayOK(@PathVariable(name = "id") String id) {
        return payClient.getPayOK(id);
    }

    @RequestMapping("/hystrix/timeout/{id}")
    public String getPayTimeOut(@PathVariable(name = "id") String id) {
        return payClient.getPayTimeOut(id);
    }
}
