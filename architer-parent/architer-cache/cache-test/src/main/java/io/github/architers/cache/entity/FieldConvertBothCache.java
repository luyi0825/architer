package io.github.architers.cache.entity;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import lombok.Data;

import java.io.Serializable;

@Data
public class FieldConvertBothCache implements Serializable {
    private String code;

    @FieldConvert(converter = "fieldConverter", sourceField = "code", cacheLevel = CacheLevel.localAndRemote)
    private String caption;

}
