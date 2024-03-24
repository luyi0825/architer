package io.github.architers.query.dynimic;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 列条件（与orm交互的列条件）
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class DynamicColumnConditions implements Serializable {

    /**
     * 查询的列信息
     */
    private List<Column> columns;

    /**
     * 动态where
     */
    private List<Where> wheres;

    /**
     * 动态排序
     */
    private List<OrderBy> orders;


    @Data
    public static class Column {

        /**
         * 字段名
         */
        private String columnName;

        /**
         * 字段别名
         */
        private String columnAlias;
    }


    /**
     * @author luyi
     * 排序
     */
    @Data
    public static class OrderBy {
        /**
         * 列名
         */
        private String columnName;
        /**
         * 降序，默认false（也就是升序）
         */
        private boolean desc = false;

        public OrderBy(String columnName) {
            this.columnName = columnName;
        }

        public OrderBy(String columnName, boolean desc) {
            this.columnName = columnName;
            this.desc = desc;
        }
    }


}
