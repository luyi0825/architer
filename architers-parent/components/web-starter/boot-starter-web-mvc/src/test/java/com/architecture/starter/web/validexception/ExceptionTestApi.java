package io.github.architers.starter.web.validexception;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     *
     */
    @PostMapping("/methodArgumentNotValidException")
    public String methodArgumentNotValidException(@RequestBody @Validated User user) {
        return "methodArgumentNotValidException";
    }

    @GetMapping("/bindException")
    public String bindException(@Validated User user) {
        return "bindException";
    }

}
