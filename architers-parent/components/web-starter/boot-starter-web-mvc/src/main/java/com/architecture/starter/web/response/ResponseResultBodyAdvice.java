package io.github.architers.starter.web.response;

import io.github.architers.context.response.R;
import io.github.architers.context.response.ResponseResult;
import io.github.architers.context.response.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 描述:自定义ResponseBodyAdvice，返回统一参数类型
 *
 * @author luyi
 * @date 2020/12/20 下午6:49
 */

@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object>, Ordered {
    private final Logger logger = LoggerFactory.getLogger(ResponseResultBodyAdvice.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseResultBodyAdvice() {
        logger.info("init ResponseResultBodyAdvice");
    }

    @Override
    @Nullable
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }


    @Override
    public int getOrder() {
        return -9;
    }


    @SneakyThrows
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Object returnValue;
        if (body instanceof ResponseResult) {
            returnValue = body;
        } else if (body instanceof R) {
            returnValue = body;
        } else {
            returnValue = ResponseResult.ok(body);
        }
        return returnValue;
    }
}
