package com.architecture.ultimate.starter.web.exception;


import com.architecture.ultimate.module.common.ResponseStatusEnum;
import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.module.common.exception.ServiceException;
import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.module.common.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
            return constraintViolationExceptionResponseResult(e);
        }
        //post请求的对象参数校验失败后抛出的异常
        if (e instanceof MethodArgumentNotValidException) {
            return getMethodArgumentNotValidExceptionResponseResult(e);
        }
        //get请求的对象参数校验失败后抛出的异常
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
    private ResponseResult getMethodArgumentNotValidExceptionResponseResult(Exception e) {
        List<ObjectError> errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
        Map<String, String> errorsMap = new HashMap<>(errors.size());
        errors.forEach(error -> {
            errorsMap.put(error.getObjectName(), error.getDefaultMessage());

        });
        return new ResponseResult(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), null, errorsMap);
    }

    /**
     * 单个校验失败的异常
     */
    private ResponseResult constraintViolationExceptionResponseResult(Exception e) {
        Set<ConstraintViolation<?>> sets = ((ConstraintViolationException) e).getConstraintViolations();
        if (!CollectionUtils.isEmpty(sets)) {
            Map<String, String> errors = new HashMap<>();
            sets.forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            });
            ResponseResult responseResult = new ResponseResult();
            responseResult.setCode(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode());
            responseResult.setData(errors);
            return responseResult;
        }
        return null;
    }

    /**
     * get请求的对象参数校验失败后抛出的异常是BindException
     */
    private ResponseResult getBindExceptionResponseResult(BindException bindException) {
        List<FieldError> fieldErrorList = bindException.getFieldErrors();
        return new ResponseResult(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), fieldErrorList.get(0).getDefaultMessage());
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
