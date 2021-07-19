package com.architecture.ultimate.es.model.ddl;


import com.architecture.ultimate.es.model.MappingType;

import java.io.Serializable;

/**
 * mapping字段项
 *
 * @author luyi
 */
public class MappingItem implements Serializable {
    /**
     * 字段
     */
    private String field;
    /**
     * 映射类型
     */
    private MappingType mappingType;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public MappingType getMappingType() {
        return mappingType;
    }

    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }
}
