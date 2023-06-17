package io.github.architers.test.webservlet;

import io.github.architers.context.exception.BusErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebServletController {

    @GetMapping("/void")
    public void voidTest() {

    }

    @GetMapping("/string")
    public String string() {
        return "test2";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new BusErrorException("test");
    }

}
