package io.github.architers.context.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 排序
 *
 * @author luyi
 */
@Data
public class OrderBy implements Serializable {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 是否升序(true升序，false降序，默认true)
     */
    private Boolean asc;
}
