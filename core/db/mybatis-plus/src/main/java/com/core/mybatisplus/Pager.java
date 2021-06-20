package com.core.mybatisplus;

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
    private Long currentPage;
    /**
     * 查询数量
     */
    private Long limit = 10L;

}
