package io.github.architers.contenxt.exception;

import io.github.architers.contenxt.response.ResponseStatusEnum;
import lombok.Getter;

/**
 * 参数校验异常
 *
 * @author luyi
 */
@Getter
public class RequestParamsValidException extends RuntimeException {

    /**
     * 异常编码，帮助异常快速定位
     */
    private final int code = ResponseStatusEnum.REQUEST_PARAMS_VALID_FAIL.getCode();

    private final String message = ResponseStatusEnum.REQUEST_PARAMS_VALID_FAIL.getMessage();

    private Object data;

    public RequestParamsValidException() {

    }

    public RequestParamsValidException(Object data) {
        this.data = data;
    }


}
