package com.business.search;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "test";
    }
}
