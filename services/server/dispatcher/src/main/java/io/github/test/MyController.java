package io.github.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class MyController {

    @PostMapping("/test1")
    public String test1(String userName) {
        System.out.println("in");
        return userName;
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") String id){
        return id;
    }

}
