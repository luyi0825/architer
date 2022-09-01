package io.github.architers.contenxt.response;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author ly
 * 请求结果
 */
public class ResponseResult implements Serializable {


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
    private Object data;

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

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String message, Object data) {
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
    public static ResponseResult ok() {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(ResponseStatusEnum.SUCCESS.getCode());
        baseResponse.setMessage(ResponseStatusEnum.SUCCESS.getMessage());
        return baseResponse;
    }

    /**
     * 成功
     */
    public static ResponseResult ok(Object data) {
        ResponseResult baseResponse = ResponseResult.ok();
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 失败
     */
    public static ResponseResult fail() {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(ResponseStatusEnum.SERVICE_EXCEPTION.getCode());
        baseResponse.setMessage(ResponseStatusEnum.SERVICE_EXCEPTION.getMessage());
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
        ResponseResult that = (ResponseResult) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }
}
