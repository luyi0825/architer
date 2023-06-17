package io.github.architers.context.web.servlet;


import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.IgnoreResponseResult;
import io.github.architers.context.web.ResponseResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * servlet对应的responseResult处理：
 * 统一返回ResponseResult实体结果，如果不想返回该实体结果就再方法上添加IgnoreResponseResult注解
 *
 * @author luyi
 * @since 1.0.2
 */
@RestControllerAdvice
@Slf4j
public class ServletResponseResultHandler implements ResponseBodyAdvice<Object>, Ordered {

    public ServletResponseResultHandler() {
        log.info("init ServletResponseResultHandler");
    }

    @Override
    @Nullable
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        IgnoreResponseResult ignoreResponseResult = returnType.getMethodAnnotation(IgnoreResponseResult.class);
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

        ResponseResult<?> responseResult;
        if (body instanceof ResponseResult) {
            responseResult = (ResponseResult<?>) body;
        } else {
            responseResult = ResponseResult.ok(body);
        }
        //统一json返回
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        //返回字符串格式处理
        if (MediaType.TEXT_PLAIN.equals(selectedContentType)) {
            return JsonUtils.toJsonString(responseResult);
        }
        return responseResult;
    }
}
