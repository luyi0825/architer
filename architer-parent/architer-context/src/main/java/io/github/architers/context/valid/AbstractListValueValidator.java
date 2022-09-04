package io.github.architers.context.valid;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述:ListValue 注解的校验类对应的抽象类
 *
 * @author luyi
 */
public abstract class AbstractListValueValidator<T> implements ConstraintValidator<ListValue, T> {

    protected Set<String> valueSet;

    /**
     * 初始化调用
     *
     * @param constraintAnnotation 对应的值
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        //判断value不能为空
        String[] values = constraintAnnotation.value();
        if (ArrayUtils.isEmpty(values)) {
            throw new IllegalArgumentException("value of ListValue is null");
        }
        //将所有的value的数据都放到set中，方便实现类使用
        valueSet = new HashSet<>(values.length);
        valueSet.addAll(Arrays.asList(values));
    }

    /**
     * 描述:是否检验通过
     *
     * @param val 需要校验的值
     */
    @Override
    public boolean isValid(T val, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(val)) {
            return false;
        }
        String strValue = null;
        if (!(val instanceof String)) {
            strValue = val.toString();
        }
        return valueSet.contains(strValue);
    }
}
