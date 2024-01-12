package io.github.architers.model.request;

import io.github.architers.model.query.page.IPagination;
import lombok.Data;

@Data
public class PaginationRequest<T> implements IPagination<T> {

    /**
     * 是否合计
     */
    private boolean needCount = true;

    /**
     * 当前页数
     */
    protected Integer pageNum = 1;

    /**
     * 每页记录数(分页数量）
     */
    protected Integer pageSize = 20;

    /**
     * 请求数据
     */
    protected T param;



}
