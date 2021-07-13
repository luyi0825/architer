package com.business.search.ddl.model;

import com.business.search.ddl.MappingType;

import java.io.Serializable;

/**
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
