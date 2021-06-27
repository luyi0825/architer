package com.core.starter.web.response;

import com.core.module.common.response.BaseResponse;
import com.core.module.common.response.R;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 描述:自定义ResponseBodyAdvice，返回统一参数类型
 *
 * @author luyi
 * @date 2020/12/20 下午6:49
 */

@RestControllerAdvice
public class BaseResponseBodyAdvice implements ResponseBodyAdvice, Ordered {
    public BaseResponseBodyAdvice() {
        System.out.println("init BaseResponseBodyAdvice");
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof BaseResponse) {
            return body;
        } else if (body instanceof R) {
            // 代码生成器
            return body;
        }
        return BaseResponse.ok(body);
    }

    @Override
    public int getOrder() {
        return -9;
    }
}
