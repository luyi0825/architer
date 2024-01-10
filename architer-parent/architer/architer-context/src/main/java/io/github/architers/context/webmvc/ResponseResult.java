package io.github.architers.context.webmvc;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ly
 * 请求结果
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public ResponseResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     */
    public static <T> ResponseResult<T> ok() {
        ResponseResult<T> baseResponse = new ResponseResult<>();
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage("操作成功");
        return baseResponse;
    }

    /**
     * 成功
     */
    public static <T> ResponseResult<T> ok(T result) {
        ResponseResult<T> baseResponse = ResponseResult.ok();
        baseResponse.setResult(result);
        return baseResponse;
    }

    /**
     * 失败
     */
    public static <T> ResponseResult<T> fail() {
        ResponseResult<T> baseResponse = new ResponseResult<>();
        // baseResponse.setCode(ResponseStatusEnum.SERVICE_EXCEPTION.getCode());
        // baseResponse.setMessage(ResponseStatusEnum.SERVICE_EXCEPTION.getMessage());
        return baseResponse;
    }

    /**
     * 失败
     */
    public static <T> ResponseResult<T> fail(String message) {
        ResponseResult<T> baseResponse = new ResponseResult<>();
        baseResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseResponse.setMessage(message);
        return baseResponse;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseResult<?> that = (ResponseResult<?>) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, result);
    }
}
