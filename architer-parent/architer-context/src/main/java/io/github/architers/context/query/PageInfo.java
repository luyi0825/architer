package io.github.architers.context.query;

import lombok.Data;

import java.util.List;

/**
 * 分页信息
 *
 * @author luyi
 */
@Data
public class PageInfo {
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 是否合计
     */
    private Boolean count = true;

    /**
     * 分页信息
     */
    private List<OrderBy> orderBys;
}
