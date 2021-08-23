package com.architecture.starter.web.exception;


import com.architecture.context.ResponseStatusEnum;
import com.architecture.context.exception.ParamsValidException;
import com.architecture.context.exception.ServiceException;
import com.architecture.context.response.R;
import com.architecture.context.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局异常处理
 *
 * <p>@RestControllerAdvice=@ControllerAdvice+@ResponseBody</p>
 *
 * @author luyi
 * @date 2020/12/9
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 缺少缺少参数异常提示
     */
    public static final String MISSING_PARAMETER_EXCEPTION_TIP = "缺少参数【{0}]】";


    public GlobalExceptionHandler() {
        logger.info("初始化全局异常处理");
    }


    /**
     * @author luyi
     * @date 2020/8/22
     * 处理系统异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult exceptionHandler(Exception e) {
        //处理校验异常
        ResponseResult responseResult = this.handleValidException(e);
        if (responseResult != null) {
            return responseResult;
        }

        if (e instanceof ServiceException) {
            //业务校验抛出的异常
            responseResult = new ResponseResult(ResponseStatusEnum.SERVICE_EXCEPTION.getCode(), e.getMessage(), null);
            // -----------请求参数校验异常------
        } else {
            logger.error(e.getMessage(), e);
            //其他的一些异常，是程序不可控制的
            responseResult = new ResponseResult(ResponseStatusEnum.SYSTEM_EXCEPTION.getCode(), ResponseStatusEnum.SYSTEM_EXCEPTION.getMessage(), null);
            return responseResult;
        }
        logger.error(e.getMessage(), e);
        return responseResult;
    }

    /**
     * 处理校验的异常
     */
    private ResponseResult handleValidException(Exception e) {
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
        //自定义的参数校验异常
        if (e instanceof ParamsValidException) {
            return new ResponseResult(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), e.getMessage());
        }
        return null;
    }

    /**
     * post请求的对象参数校验失败后抛出的异常
     */
    private ResponseResult getBindExceptionResponseResult(BindException e) {
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
        return new ResponseResult(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), message, errorsMap);
    }

    /**
     * 单个校验失败的异常
     */
    private ResponseResult constraintViolationExceptionResponseResult(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> sets = e.getConstraintViolations();
        if (!CollectionUtils.isEmpty(sets)) {
            Map<String, String> errors = new HashMap<>(sets.size());
            ResponseResult responseResult = new ResponseResult();
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
            responseResult.setCode(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode());
            responseResult.setData(errors);
            return responseResult;
        }
        return null;
    }

    /**
     * 缺少参数的异常
     */
    private ResponseResult getMissingServletRequestParameterExceptionResponseResult(MissingServletRequestParameterException e) {
        String message = MessageFormat.format(MISSING_PARAMETER_EXCEPTION_TIP, e.getParameterName());
        return new ResponseResult(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), message);
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }


}
