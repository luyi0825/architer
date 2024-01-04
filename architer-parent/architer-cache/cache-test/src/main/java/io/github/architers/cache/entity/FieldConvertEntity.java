package io.github.architers.cache.entity;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import lombok.Data;

@Data
public class FieldConvertEntity {
    private String code;

    @FieldConvert(converter = "fieldConverter", sourceField = "code", cacheLevel = CacheLevel.none)
    private String caption;
}
