//package com.ly;
//
//import com.alibaba.fastjson.JSON;
//import com.ly.core.expection.BusException;
//import com.ly.core.expection.StatusCode;
//import com.ly.core.response.BaseResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.HttpMessageReader;
//import org.springframework.http.codec.HttpMessageWriter;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.web.authentication.session.SessionAuthenticationException;
//import org.springframework.util.Assert;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.reactive.result.view.ViewResolver;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class JsonExceptionHandler implements ErrorWebExceptionHandler {
//    private static final Logger log = LoggerFactory.getLogger(JsonExceptionHandler.class);
//
//    /**
//     * MessageReader
//     */
//    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
//
//    /**
//     * MessageWriter
//     */
//    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
//
//    /**
//     * ViewResolvers
//     */
//    private List<ViewResolver> viewResolvers = Collections.emptyList();
//
//    /**
//     * 存储处理异常后的信息
//     */
//    private ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();
//
//    /**
//     * 参考AbstractErrorWebExceptionHandler
//     */
//    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
//        Assert.notNull(messageReaders, "'messageReaders' must not be null");
//        this.messageReaders = messageReaders;
//    }
//
//    /**
//     * 参考AbstractErrorWebExceptionHandler
//     */
//    public void setViewResolvers(List<ViewResolver> viewResolvers) {
//        this.viewResolvers = viewResolvers;
//    }
//
//    /**
//     * 参考AbstractErrorWebExceptionHandler
//     */
//    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
//        Assert.notNull(messageWriters, "'messageWriters' must not be null");
//        this.messageWriters = messageWriters;
//    }
//
//    @Override
//    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
//        //错误记录
//        ServerHttpRequest request = exchange.getRequest();
//        // 按照异常类型进行处理
//        int status = 200;
//        String body = null;
//        HttpHeaders requestHeaders = request.getHeaders();
//        ServerHttpResponse response = exchange.getResponse();
//        HttpMethod requestMethod = exchange.getRequest().getMethod();
//        if(requestMethod.equals(HttpMethod.OPTIONS)){
//            return Mono.empty();
//        }
//        HttpHeaders headers = response.getHeaders();
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
//        headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
//        if (requestMethod != null) {
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
//        }
//        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
//        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
//
//        BaseResponse baseResponse;
//        if (ex instanceof BusException) {
//            BusException busException = (BusException) ex;
//            StatusCode statusCode = busException.getStatusCode();
//            if (statusCode != null) {
//                baseResponse = new BaseResponse(statusCode);
//            } else {
//                baseResponse = new BaseResponse(StatusCode.BUS_EXCEPTION);
//            }
//        } else if(ex instanceof SessionAuthenticationException) {
//            baseResponse = new BaseResponse(StatusCode.NO_LOGIN);
//        }else {
//            baseResponse = new BaseResponse(StatusCode.SYSTEM_EXCEPTION);
//        }
//        //封装响应体,此body可修改为自己的jsonBody
//        Map<String, Object> result = new HashMap<>(2, 1);
//        result.put("httpStatus", status);
//
//        String msg = JSON.toJSONString(baseResponse);
//        result.put("body", msg);
//
//        log.error("[全局异常处理]异常请求路径:{},记录异常信息:{}", request.getPath(), ex.getMessage());
//        //参考AbstractErrorWebExceptionHandler
//        if (exchange.getResponse().isCommitted()) {
//            return Mono.error(ex);
//        }
//        exceptionHandlerResult.set(result);
//        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
//                .switchIfEmpty(Mono.error(ex))
//                .flatMap((handler) -> handler.handle(newRequest))
//                .flatMap((response1) -> write(exchange, response1));
//
//    }
//
//    /**
//     * 参考DefaultErrorWebExceptionHandler
//     */
//    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
//        Map<String, Object> result = exceptionHandlerResult.get();
//        return ServerResponse.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(BodyInserters.fromObject(result.get("body")));
//    }
//
//    /**
//     * 参考AbstractErrorWebExceptionHandler
//     */
//    private Mono<? extends Void> write(ServerWebExchange exchange,
//                                       ServerResponse response) {
//        exchange.getResponse().getHeaders()
//                .setContentType(response.headers().getContentType());
//        return response.writeTo(exchange, new ResponseContext());
//    }
//
//    /**
//     * 参考AbstractErrorWebExceptionHandler
//     */
//    private class ResponseContext implements ServerResponse.Context {
//
//        @Override
//        public List<HttpMessageWriter<?>> messageWriters() {
//            return JsonExceptionHandler.this.messageWriters;
//        }
//
//        @Override
//        public List<ViewResolver> viewResolvers() {
//            return JsonExceptionHandler.this.viewResolvers;
//        }
//
//    }
//
//
//}
