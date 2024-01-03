package io.github.architers.cache.entity;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.fieldconvert.anantion.NeedFieldConvert;
import lombok.Data;

import java.io.Serializable;

/**
 * 字段转换信息(每次)
 *
 * @author luyi
 */
@Data
public class FieldConvertNoCache implements Serializable {

    private String code;

    @FieldConvert(converter = "fieldConverter", sourceField = "code", cacheLevel = CacheLevel.none)
    private String caption;

    @NeedFieldConvert
    private FieldConvertNoCache son;
}
