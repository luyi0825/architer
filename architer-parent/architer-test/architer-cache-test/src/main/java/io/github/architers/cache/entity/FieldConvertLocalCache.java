package io.github.architers.cache.entity;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import lombok.Data;

import java.io.Serializable;

@Data
public class FieldConvertLocalCache implements Serializable {
    private String code;

    @FieldConvert(converter = "fieldConverter", sourceField = "code", cacheLevel = CacheLevel.local)
    private String caption;

}
