package io.github.architers.center.menu;


import lombok.Getter;

/**
 * 状态枚举
 *
 * @author luyi
 */
@Getter
public enum StatusEnum {

    /**
     * 禁用
     */
    DISABLED((byte) 0, "禁用"),

    /**
     * 启动
     */
    ENABLED((byte) 1, "启用");

    private final Byte status;
    private final String caption;

    StatusEnum(Byte status, String caption) {
        this.status = status;
        this.caption = caption;
    }
}
