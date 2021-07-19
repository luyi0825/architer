package com.architecture.ultimate.query.common.model;

import com.architecture.ultimate.query.common.OperatorEnum;

import java.io.Serializable;

/**
 * @author luyi
 * 查询条件
 */
public class QueryCondition implements Serializable {
    /**
     * 查询字段
     */
    private String field;
    /**
     * 操作条件
     */
    private OperatorEnum operator;
    /**
     * 操作值
     */
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
