package com.architecture.context.request;

import lombok.Data;

/**
 * @author luyi
 * 排序
 */
@Data
public class OrderBy {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 默认升序
     */
    private boolean asc = true;
}
