package com.core.test.consumer.zipkin.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.core.test.consumer.zipkin.feign.ProviderZipkinFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 * zipkin测试的控制层
 */
@RequestMapping("/zipkin")
@RestController
public class ZipkinController {
    @Autowired(required = false)
    private ProviderZipkinFeign providerZipkinFeign;

    @RequestMapping("/get/{id}")
    @SentinelResource
    public String getXXX(@PathVariable(name = "id") String id) {
        return providerZipkinFeign.getXXX(id);
    }




}
