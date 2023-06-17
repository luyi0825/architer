package io.github.architers.test.webflux;

import io.github.architers.context.exception.BusErrorException;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.web.IgnoreResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebFluxController {

    @GetMapping("/void")
    public void voidTest() {

    }

    @GetMapping("/string")
    public String string() {
        return "test2";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new BusException("异常");
    }

    @GetMapping("/ignoreResponse")
    @IgnoreResponseResult
    public String ignoreResponse() {
        return "ignoreResponse";
    }

}
