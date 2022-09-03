package com.architecture.query.common.model;



import java.io.Serializable;

/**
 * @author luyi
 */
public class Pager implements Serializable {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 查询数量
     */
    private Integer limit;

    public Pager() {
        this.currentPage = 1;
        this.limit = 10;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
