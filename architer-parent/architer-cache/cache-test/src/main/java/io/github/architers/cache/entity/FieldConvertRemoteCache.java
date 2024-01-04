package io.github.architers.cache.entity;

import com.sun.source.doctree.SerialDataTree;
import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import lombok.Data;

import java.io.Serializable;

@Data
public class FieldConvertRemoteCache implements Serializable {
    private String code;

    @FieldConvert(converter = "fieldConverter", sourceField = "code", cacheLevel = CacheLevel.remote)
    private String caption;

}
