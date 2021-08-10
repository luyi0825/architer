package com.architecture.ultimate.starter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常测试的api接口
 */
@RestController
@RequestMapping("/exceptionTestApi")
public class ExceptionTestApi {

    @GetMapping("/missingServletRequestParameterException")
    public String missingServletRequestParameterException(@RequestParam(name = "test") int test) {
        return test + "";
    }
}
