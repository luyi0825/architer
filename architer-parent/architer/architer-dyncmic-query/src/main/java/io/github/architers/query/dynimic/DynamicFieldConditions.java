package io.github.architers.query.dynimic;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 字段条件（前端传参的字段条件，转成ColumnConditions后才能跟数据库交互）
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class DynamicFieldConditions {

    private Set<String> fieldNames;

    /**
     * 动态where
     */
    private List<Where> wheres;



    private List<OrderBy> orders;


    /**
     * @author luyi
     * 排序
     */
    @Data
    public static class OrderBy {
        /**
         * 列名
         */
        private String fieldName;
        /**
         * 降序，默认false（也就是升序）
         */
        private boolean desc = false;

        public OrderBy(String fieldName, boolean desc) {
            this.fieldName = fieldName;
            this.desc = desc;
        }

        public OrderBy(String fieldName) {


            this.fieldName = fieldName;
        }
    }


}
