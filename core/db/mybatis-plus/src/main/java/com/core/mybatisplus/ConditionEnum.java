package com.core.mybatisplus;


/**
 * 查询的枚举字段
 *防止页面传参数错误
 * @author luyi
 * @date 2020/12/27
 */

public enum ConditionEnum  {

    /**
     * 查询操作的类型
     */
    start("start"),
    like("like"),
    equal("equal");

    ConditionEnum(String operation) {
        this.operation = operation;
    }

    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
