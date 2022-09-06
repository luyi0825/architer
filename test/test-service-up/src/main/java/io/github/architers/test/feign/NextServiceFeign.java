package io.github.architers.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 下游服务远程调用
 *
 * @author luyi
 */
@FeignClient("next-service")
public interface NextServiceFeign {

    @GetMapping("/next/test")
    String test();


}
