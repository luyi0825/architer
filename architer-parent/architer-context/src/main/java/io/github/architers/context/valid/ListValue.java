package io.github.architers.context.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;

/**
 * 描述:自定义的校验注解-数据在列举的参数范围内
 *
 * @author luyi
 * @date 2020/12/20 上午1:04
 */
@Documented
//支持校验String和Number类型
@Constraint(
        validatedBy = {ListValueValidatorForString.class, ListValueValidatorForNumber.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
//支持重复注解
@Repeatable(ListValues.class)
public @interface ListValue {

    String message() default "{javax.validation.constraints.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value();



}
