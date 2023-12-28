package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.enums.CacheLevel;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

@Data
public class FieldConvertContext {

    /**
     * 转换的字段
     */
    private Field convertField;

    /**
     * 通过什么字段转换
     */
    private Field sourceField;

    /**
     * 原始值索引(通过转换器和sourceField拼接)
     */
    private String originValueIndex;

    /**
     * 缓存级别
     */
    private CacheLevel cacheLevel;


    private FieldConvert fieldConvert;


    public String getOriginValueConverter() {
        if (!StringUtils.isEmpty(fieldConvert.originValueConverter())) {
            return fieldConvert.originValueConverter();
        }
        return fieldConvert.converter();
    }

    public String getSourceFieldStrValue(Object data) {
        //SourceField为空，那么转换的字段也为空
        Object sourceFieldValue;
        try {
            this.getSourceField().setAccessible(true);
            sourceFieldValue = this.getSourceField().get(data);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(sourceFieldValue)) {
            //为空返回
            return null;
        }
        return sourceFieldValue.toString();
    }


}
