package io.github.architers.syscenter.user.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuNode implements Serializable {

    private Long id;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuCaption;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 父级编码
     */
    private String parentCode;

}
