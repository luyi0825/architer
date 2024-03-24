package io.github.architers.query.dynimic;


import lombok.Getter;

/**
 * where条件的操作符
 *
 * @author luyi
 * @since 1.0.3
 */
@Getter
public enum WhereOperator {

    /**
     * like '%xxx'
     */
    likeLeft,
    /**
     * like '%xxx%'
     */
    like,
    /**
     * like 'xx%'
     */
    likeRight,
    /**
     * 等于=
     */
    equal,

    /**
     * 不等于
     */
    notEqual,

    /**
     * 大于
     */
    gt,

    /**
     * 大于等于>=
     */
    ge,

    /**
     * 小于<
     */
    lt,

    /**
     * 小于等于<=
     */
    le("le"),

    /**
     * xx between a and b,也就是 a<=x<=b
     */
    between("between"),

    /**
     * 不在什么范围内
     */
    notBetween,

    in,

    notIn;

    WhereOperator() {
    }

    private String code;

    private String operator;

    WhereOperator(String code) {
        this.code = code;
    }

    WhereOperator(String code, String operator) {
        this.code = code;
        this.operator = operator;
    }
}
