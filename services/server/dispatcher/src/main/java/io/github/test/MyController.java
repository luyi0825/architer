package io.github.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MyController {

    @PostMapping("/test1")
    public String test1(String userName) {
        System.out.println("in");
        return userName;
    }

}
