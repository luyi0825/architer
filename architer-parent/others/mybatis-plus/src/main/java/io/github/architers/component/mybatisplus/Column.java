package io.github.architers.component.mybatisplus;

import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * 列信息
 *
 * @author luyi
 */
@Data
public class Column {

    /**
     * 查询的字段
     */
    private String field;

    /**
     * 别名
     */
    private String alias;

    public Column() {
    }

    public Column(String field, String alias) {
        this.field = field;
        this.alias = alias;
    }
}
