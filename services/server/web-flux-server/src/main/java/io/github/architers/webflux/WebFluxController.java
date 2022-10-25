package io.github.architers.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
public class WebFluxController {

    @GetMapping("/test")
    public String test() {
        return "1";
    }
}
