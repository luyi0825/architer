package io.github.architers.syscenter.user.domain.entity;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class SysMenuListColumn {
    /**
     * 列ID
     */
    private Long id;

    /**
     * 展示名称
     */
    private String showCaption;

    /**
     * sql字段
     */
    private String sqlColumn;

    /**
     * sql对应的别名（也就是列名）
     */
    private String alias;

    /**
     * 宽
     */
    private String width;

    /**
     * 顺序
     */
    private Integer order;

}
