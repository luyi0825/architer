package io.github.architers.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class ResponseResult<T> implements Serializable {

    /**
     * 编码
     */
    private int code;

    /**
     * code对应的消息
     */
    private String message;

    /**
     * 返回给前端的数据
     */
    private T data;


}
