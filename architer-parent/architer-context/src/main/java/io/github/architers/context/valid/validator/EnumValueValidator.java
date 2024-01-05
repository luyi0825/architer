package io.github.architers.context.valid.validator;

import io.github.architers.context.valid.annotion.EnumValue;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 校验字段值是枚举字段中的值
 *
 * @author luyi
 * @since 1.0.3
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private Set<Object> values;

    private boolean force;

    @Override
    public void initialize(EnumValue enumValue) {
        //枚举的class
        Class<?> enumClass = enumValue.value();
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("value of @enumValue is not enum class");
        }
        values = new HashSet<>(enumClass.getEnumConstants().length, 1);
        force = enumValue.force();
        for (Object enumConstant : enumClass.getEnumConstants()) {
            try {
                Object value;
                if (StringUtils.hasText(enumValue.checkField())) {
                    Field field = enumConstant.getClass().getDeclaredField(enumValue.checkField());
                    field.setAccessible(true);
                    value = field.get(enumConstant);
                } else if (enumConstant instanceof EnumValueValid) {
                    EnumValueValid enumValueValid = (EnumValueValid) enumConstant;
                    value = enumValueValid.getValidField();
                } else {
                    throw new RuntimeException("校验的字段值没有配置");
                }
                if (enumValue.force() || value == null) {
                    values.add(value);
                } else if (value instanceof Number) {
                    values.add(value.toString());
                } else if (value instanceof String) {
                    values.add(value);
                } else if (value instanceof Boolean) {
                    values.add(value);
                } else {
                    throw new RuntimeException("暂不支持:" + value.getClass());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (force) {
            //强制
            return values.contains(value);
        }

        if (value != null) {
            return values.contains(value.toString());
        }

        return values.contains(null);

    }
}
