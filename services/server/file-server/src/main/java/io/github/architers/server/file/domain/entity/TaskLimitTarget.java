package io.github.architers.server.file.domain.entity;

import lombok.Getter;

/**
 * 限制对象
 *
 * @author luyi
 */
@Getter
public enum TaskLimitTarget {

    /**
     * 可以针对单个IP限制
     */
    IP(1, "IP地址"),
    /**
     * 可以针对单个用户限制
     */
    USER(2, "用户"),
    /**
     * 可以针对参数限制：比如一个项目下，只能有一个用户在导入某个业务数据
     */
    params(3, "参数");

    /**
     * 所有：比如一个系统，同一时刻只能有十个人在下载
     */




    private final int code;

    private final String caption;

    TaskLimitTarget(Integer code, String caption) {
        this.code = code;
        this.caption = caption;
    }
}
