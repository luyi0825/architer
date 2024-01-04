package io.github.architers.context.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 描述:ListValue 注解的校验器 -- 数值
 *
 * @author luyi
 * @date 2020/12/20 上午1:13
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private Set<Object> values;

    @Override
    public void initialize(EnumValue enumValue) {
        //枚举的class
        Class<?> enumClass = enumValue.value();
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("value of @enumValue is not enum class");
        }
        values = new HashSet<>(enumClass.getEnumConstants().length, 1);
        for (Object enumConstant : enumClass.getEnumConstants()) {
            try {
                Field field = enumConstant.getClass().getDeclaredField(enumValue.field());
                field.setAccessible(true);
                Object value = field.get(enumConstant);
                values.add(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return values.contains(value);
    }
}
