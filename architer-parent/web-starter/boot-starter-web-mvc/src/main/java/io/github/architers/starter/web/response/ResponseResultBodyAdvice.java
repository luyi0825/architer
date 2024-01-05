package io.github.architers.starter.web.response;


import io.github.architers.context.webmvc.IgnoreResponseResult;
import io.github.architers.context.webmvc.ResponseResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object>, Ordered {

    public ResponseResultBodyAdvice() {
        log.info("init ResponseResultBodyAdvice");
    }

    @Override
    @Nullable
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        IgnoreResponseResult ignoreResponseResult =
                returnType.getMethodAnnotation(IgnoreResponseResult.class);
        return ignoreResponseResult == null;
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

        if (body instanceof ResponseResult) {
            return body;
        }
        return ResponseResult.ok(body);
    }
}
