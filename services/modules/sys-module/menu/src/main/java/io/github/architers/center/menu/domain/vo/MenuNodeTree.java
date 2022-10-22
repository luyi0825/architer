package io.github.architers.center.menu.domain.vo;

import java.util.List;

public class MenuNodeTree {

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
     * 父级ID
     */
    private String parentId;

    private List<MenuNodeTree> childrenList;
}
