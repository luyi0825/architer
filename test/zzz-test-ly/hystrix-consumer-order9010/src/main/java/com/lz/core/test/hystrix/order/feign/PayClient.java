package com.lz.core.test.hystrix.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author luyi
 * Pay对应的远程调用
 */
@Component
@FeignClient(value = "hystrix-provider-zk-payment")
@RequestMapping("/pay")
public interface PayClient {
    @RequestMapping("/hystrix/ok/{id}")
    String getPayOK(@PathVariable(name = "id") String id);

    @RequestMapping("/hystrix/timeout/{id}")
    String getPayTimeOut(@PathVariable(name = "id") String id);
}
