package io.github.architers.contenxt.request;

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
     * 是否升序(true升序，false降序，默认true)
     */
    private Boolean asc;
}
