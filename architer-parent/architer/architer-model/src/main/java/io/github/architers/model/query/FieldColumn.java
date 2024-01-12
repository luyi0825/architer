package io.github.architers.model.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询字段
 *
 * @author luyi
 */
@Data
public class FieldColumn implements Serializable {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 展示的字段名
     */
    private String fieldName;

    /**
     * 字段别名
     */
    private String alias;
}
