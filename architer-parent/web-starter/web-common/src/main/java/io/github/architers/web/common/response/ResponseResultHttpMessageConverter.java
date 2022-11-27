package io.github.architers.web.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.architers.context.web.ResponseResult;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author luyi
 * 自定义消息转换器
 */
public class ResponseResultHttpMessageConverter extends AbstractHttpMessageConverter<ResponseResult> {
    private final ObjectMapper objectMapper;

    public ResponseResultHttpMessageConverter(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return ResponseResult.class.isAssignableFrom(clazz);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ResponseResult.class.isAssignableFrom(clazz);
    }

    @Override
    protected ResponseResult<?> readInternal(Class<? extends ResponseResult> clazz,
    HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(ResponseResult responseResult,
                                 HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        StreamUtils.copy(objectMapper.writeValueAsString(responseResult), Charset.defaultCharset(), outputMessage.getBody());
    }


}
