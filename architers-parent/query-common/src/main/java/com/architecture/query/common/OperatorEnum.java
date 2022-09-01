package io.github.architers.query.common;


/**
 * 查询的枚举字段
 * 防止页面传参数错误
 *
 * @author luyi
 * @date 2020/12/27
 */

public enum OperatorEnum {

    /**
     * 查询操作的类型
     */
    START("start"),
    LIKE("like"),
    EQUALS("equals");

    OperatorEnum(String operation) {
        this.operation = operation;
    }

    String operation;

    public String getOperation() {
        return operation;
    }

}
