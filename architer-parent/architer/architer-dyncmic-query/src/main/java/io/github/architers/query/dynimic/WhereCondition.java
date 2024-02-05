package io.github.architers.query.dynimic;

import lombok.Data;

import java.io.Serializable;

@Data
public class WhereCondition implements Serializable {
    /**
     * 查询列名
     */
    private String columnName;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 操作条件
     */
    private WhereOperator operator;
    /**
     * 操作值
     */
    private Object value;


    public WhereCondition() {
    }

    public WhereCondition(String fieldName, WhereOperator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }
}
