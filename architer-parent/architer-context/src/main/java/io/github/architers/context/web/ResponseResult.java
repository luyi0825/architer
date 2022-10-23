package io.github.architers.context.web;


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
    private T data;

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

    public Object getData() {
        return data;
    }


    public ResponseResult() {

    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
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
    public static <T> ResponseResult<T> ok(T data) {
        ResponseResult<T> baseResponse = ResponseResult.ok();
        baseResponse.setData(data);
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
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }
}
