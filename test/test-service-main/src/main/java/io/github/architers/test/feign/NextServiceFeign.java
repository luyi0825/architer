package io.github.architers.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("next-service")
@RequestMapping("/next")
public interface NextServiceFeign {

    @GetMapping("/test")
    String test();


}
