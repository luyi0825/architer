package io.github.architers.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下游服务控制层
 *
 * @author uyi
 */
@RestController
@RequestMapping("/next")
public class NextController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/test")
    public String test() {
        return "next" + port;
    }
}
