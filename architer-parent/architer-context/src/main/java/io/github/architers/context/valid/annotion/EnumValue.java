package io.github.architers.context.valid.annotion;

import io.github.architers.context.valid.validator.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {EnumValueValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {

    String message() default "值不在指定范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> value();

    /**
     * 检验的字段
     */
    String checkField() default "";

    /**
     * 是否强制校验：true会判断类型相同，false都转成字符串比较
     */
    boolean force() default false;
}
