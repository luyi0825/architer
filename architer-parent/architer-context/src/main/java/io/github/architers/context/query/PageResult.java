package io.github.architers.context.query;

import lombok.Data;

import java.util.List;

/**
 * 分页结果
 * @author luyi
 */
@Data
public class PageResult<T> {
    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总条数
     */
    private long total;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 当前页数
     */
    private int currentPage;

    /**
     * 每页显示的条数
     */
    private int pageSize;
}
