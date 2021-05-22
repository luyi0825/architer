package com.lz.core.test.consumer.zipkin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author luyi
 * 提供者的远程调用的feign
 */
@FeignClient("cloud-alibaba-provider")
public interface ProviderZipkinFeign {

    @RequestMapping("/zipkin/get/{id}")
    String getXXX(@PathVariable(name = "id") String id);
}
