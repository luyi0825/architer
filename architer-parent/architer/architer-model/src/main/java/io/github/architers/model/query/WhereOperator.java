package io.github.architers.model.query;


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
    LIKE_LEFT("likeLeft'"),
    /**
     * like '%xxx%'
     */
    LIKE("like"),
    /**
     * like 'xx%'
     */
    LIKE_RIGHT("likeRight'"),
    /**
     * 等于=
     */
    EQ("eq"),

    /**
     * 不等于
     */
    NE("ne"),

    /**
     * 大于
     */
    GT(""),

    /**
     * 大于等于>=
     */
    GE("ge"),

    /**
     * 小于<
     */
    LT("lt"),

    /**
     * 小于等于<=
     */
    LE("le"),

    /**
     * xx between a and b,也就是 a<=x<=b
     */
    BETWEEN("between"),

    /**
     *
     */
    NOT_BETWEEN("notBetween");


    private String code;

    WhereOperator(String code) {
        this.code = code;
    }


}
