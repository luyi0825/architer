/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.github.architers.common.model.page;

import java.io.Serializable;

/**
 * 分页
 *
 * @author luyi
 * @since 1.0.3
 */
public interface IPagination<T> extends Serializable {

    /**
     * 分页前是否count,false则不count
     */
    boolean isNeedCount();

    /**
     * 得到当前页
     */
    Integer getPageNum();

    /**
     * 设置查询的当前页
     *
     * @param pageNum 当前页面
     */
    void setPageNum(Integer pageNum);

    /**
     * 得到每页的数量
     */
    Integer getPageSize();

    /**
     * 设置每页的数量
     */
    void setPageSize(Integer pageSize);

    /**
     * 清理分页参数(清空了会查询所有)
     */
    default void cleanPageParam() {
        setPageNum(null);
        setPageSize(null);
    }

}
