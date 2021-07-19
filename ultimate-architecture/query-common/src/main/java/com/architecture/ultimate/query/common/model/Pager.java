package com.architecture.ultimate.query.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
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

}
