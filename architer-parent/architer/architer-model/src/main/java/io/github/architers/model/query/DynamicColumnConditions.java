package io.github.architers.model.query;

import lombok.Data;

import java.util.List;

/**
 * 列条件（与orm交互的列条件）
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class DynamicColumnConditions {

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


    @Data
    public static class Where {
        /**
         * 查询字段名
         */
        private String columnName;

        /**
         * 操作条件
         */
        private WhereOperator operator;
        /**
         * 操作值
         */
        private Object value;

        private Object convertValue;

        /**
         * 对应对应的sql
         */
        private String sql;
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
