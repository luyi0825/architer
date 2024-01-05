package io.github.architers.context.exception;

import io.github.architers.context.webmvc.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认的异常处理器
 *
 * @author luyi
 */
@Slf4j
public class DefaultExceptionHandler implements RequestExceptionHandler {

    /**
     * 缺少缺少参数异常提示
     */
    public static final String MISSING_PARAMETER_EXCEPTION_TIP = "缺少参数【{0}】";

    @Override
    public ResponseResult<?> handler(Throwable e) {
        if (e instanceof BusException) {
            BusException noStackBusException = (BusException) e;
            return new ResponseResult<>(noStackBusException.getCode(), e.getMessage());
        }
        //处理校验异常
        ResponseResult<?> responseResult = this.handleValidException(e);
        //参数异常不记录异常信息
        if (responseResult != null) {
            return responseResult;
        }
        //没有权限访问
        if (e instanceof AccessDeniedException) {
            return new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "没有权限");
        }
        //业务主动抛出异常
        log.error(e.getMessage(), e);
        if (e instanceof BusLogException) {
            return new ResponseResult<>(((BusLogException) e).getCode(), e.getMessage());
        }
        //不可预期的异常
        return new ResponseResult<>(SysException.SYS_EXCEPTION_CODE, SysException.SYS_EXCEPTION_MESSAGE, e.getMessage());
    }

    /**
     * 处理校验的异常
     */
    private ResponseResult<?> handleValidException(Throwable e) {
        //缺少参数抛出的异常
        if (e instanceof MissingServletRequestParameterException) {
            return this.getMissingServletRequestParameterExceptionResponseResult((MissingServletRequestParameterException) e);
        }
        //单个校验失败的异常
        if (e instanceof ConstraintViolationException) {
            return constraintViolationExceptionResponseResult((ConstraintViolationException) e);
        }
        /*get请求的对象参数校验失败后抛出BindException异常,post抛出MethodArgumentNotValidException，
        而且MethodArgumentNotValidException instanceof BindException*/
        if (e instanceof BindException) {
            return getBindExceptionResponseResult((BindException) e);
        }

        return null;
    }

    /**
     * post请求的对象参数校验失败后抛出的异常
     */
    private ResponseResult<?> getBindExceptionResponseResult(BindException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        Map<String, String> errorsMap = new HashMap<>(errors.size());
        String message = null;
        for (ObjectError error : errors) {
            if (message == null) {
                message = error.getDefaultMessage();
            }
            if (error instanceof FieldError) {
                errorsMap.put(((FieldError) error).getField(), error.getDefaultMessage());
            }
        }
        return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(), message, errorsMap);
    }

    /**
     * 单个校验失败的异常
     */
    private ResponseResult<?> constraintViolationExceptionResponseResult(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> sets = e.getConstraintViolations();
        if (!CollectionUtils.isEmpty(sets)) {
            Map<String, String> errors = new HashMap<>(sets.size());
            ResponseResult<Map<String, String>> responseResult = new ResponseResult<>();
            sets.forEach(error -> {
                if (error.getPropertyPath() instanceof PathImpl) {
                    PathImpl path = (PathImpl) error.getPropertyPath();
                    errors.put(path.getLeafNode().getName(), error.getMessage());
                }
                //取第一个做message
                if (StringUtils.isEmpty(responseResult.getMessage())) {
                    responseResult.setMessage(error.getMessage());
                }
            });
            responseResult.setCode(HttpStatus.BAD_REQUEST.value());
            responseResult.setResult(errors);
            return responseResult;
        }
        return null;
    }

    /**
     * 缺少参数的异常
     */
    private ResponseResult<?> getMissingServletRequestParameterExceptionResponseResult(MissingServletRequestParameterException e) {
        String message = MessageFormat.format(MISSING_PARAMETER_EXCEPTION_TIP, e.getParameterName());
        return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(), message);
    }


}
