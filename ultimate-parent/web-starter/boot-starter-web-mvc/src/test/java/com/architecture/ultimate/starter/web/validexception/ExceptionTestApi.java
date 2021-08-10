package com.architecture.ultimate.starter.web.validexception;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * 异常测试的api接口
 */
@RestController
@RequestMapping("/exceptionTestApi")
@Validated
public class ExceptionTestApi {

    /**
     * 缺失参数
     */
    @GetMapping("/missingServletRequestParameterException")
    public String missingServletRequestParameterException(@RequestParam(name = "test") int test) {
        return test + "";
    }

    /**
     * 单个校验失败的异常
     */
    @GetMapping("/constraintViolationException")
    public String constraintViolationException(@Length(min = 10, message = "最小长度为10") @RequestParam(name = "test") String test,
                                               @Length(min = 10, message = "最小长度为10") @RequestParam(name = "test2") String test2) {
        return test + "@" + test2;
    }
}
