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
public class ColumnConditions {

    private List<Column> columns;

    private List<Where> wheres;


    private List<OrderBy> orders;


    @Data
    public static class Column {

        /**
         * 字段名
         */
        private String fieldName;

        /**
         * 列名
         */
        private String columnName;

        /**
         * 字段别名
         */
        private String alias;
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
        private String value;
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
