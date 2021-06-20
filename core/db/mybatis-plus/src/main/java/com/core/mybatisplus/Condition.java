package com.core.mybatisplus;

import lombok.Data;

/**
 * @author luyi
 * @date 2020、12、27
 * 查询条件
 */
@Data
public class Condition {
    private String field;
    private Object value;
    /**
     * 查询条件
     */
    private ConditionEnum condition;
}
