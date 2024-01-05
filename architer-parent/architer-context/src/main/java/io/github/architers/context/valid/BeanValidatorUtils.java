
package io.github.architers.context.valid;


import io.github.architers.context.exception.BusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author luyi
 */
public final class BeanValidatorUtils {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static Map<String/*错误的字段*/, String/*错误信息*/> validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            Map<String, String> errorMap = new HashMap<>(constraintViolations.size(), 1);
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                errorMap.put(constraint.getPropertyPath().toString(), constraint.getMessage());
            }
            return errorMap;
        }
        return Collections.emptyMap();
    }
}
