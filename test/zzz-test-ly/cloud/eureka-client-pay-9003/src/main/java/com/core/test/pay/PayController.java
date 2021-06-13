package com.core.test.pay;

import com.lz.core.service.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Value("${server.port}")
    private String port;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/get/{id}")
    public BaseResponse get(@PathVariable(name = "id") String id) {
        return BaseResponse.ok(id + "xx:" + port);
    }

    @RequestMapping("/getServices/{applicaion}")
    public BaseResponse getServices(@PathVariable(name = "applicaion") String applicationName) {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(applicationName);
        return BaseResponse.ok(serviceInstanceList);
    }
}
