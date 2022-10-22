package io.github.architers.context.query;

import lombok.Data;

import java.util.List;

/**
 * 分页信息
 *
 * @author luyi
 */
@Data
public class PageParam {
    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 每页数量
     */
    private Integer pageSize = 15;

    /**
     * 是否合计
     */
    private Boolean count = true;

    /**
     * 分页信息
     */
    private List<OrderBy> orderBys;
}
