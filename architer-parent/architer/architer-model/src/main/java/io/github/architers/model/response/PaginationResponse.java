/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.github.architers.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应参数
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class PaginationResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 页码
     */
    private int pageNum;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param pageNum    当前页数
     */
    public PaginationResponse(long totalCount, int pageNum, int pageSize, int totalPage, List<T> list) {
        this.totalCount = totalCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.list = list;
    }
}
