package com.core.test.consumer.zipkin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author luyi
 * 提供者的远程调用的feign
 */
@FeignClient(value = "cloud-alibaba-provider", fallback = ProviderZipkinFeign.ProviderZipkinFeignFallback.class, configuration = ProviderZipkinFeign.FeignConfiguration.class)
public interface ProviderZipkinFeign {

    @RequestMapping("/zipkin/get/{id}")
    String getXXX(@PathVariable(name = "id") String id);

    class FeignConfiguration {
        @Bean
        public ProviderZipkinFeignFallback echoServiceFallback() {
            return new ProviderZipkinFeignFallback();
        }
    }

    class ProviderZipkinFeignFallback implements ProviderZipkinFeign {
        @Override
        public String getXXX(String id) {
            return "ProviderZipkinFeignFallback";
        }
    }
}
