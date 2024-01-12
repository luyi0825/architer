package io.github.architers.model.query;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询条件工具类
 *
 * @author luyi
 * @since 1.0.3
 */
public final class ConditionUtils {

    public static ColumnConditions convertToColumnConditions(String conditionCode, FieldConditions fieldConditions) {

        //查询的列
        List<ColumnConditions.Column> columns = DynamicColumnManager.getByCodeAndFieldNames(conditionCode, fieldConditions.getFieldNames());
        if (columns == null) {
            return null;
        }
        ColumnConditions columnConditions = new ColumnConditions();
        columnConditions.setColumns(columns);

        Map<String/*字段名*/, String/*列名*/> fieldColumnMap = DynamicColumnManager.getFieldColumns(conditionCode);

        //where条件
        if (!CollectionUtils.isEmpty(fieldConditions.getWheres())) {
            List<ColumnConditions.Where> wheres = new ArrayList<>(fieldConditions.getWheres().size());
            for (FieldConditions.Where where : fieldConditions.getWheres()) {
                ColumnConditions.Where columnConditionsWhere = new ColumnConditions.Where();
                columnConditionsWhere.setColumnName(fieldColumnMap.get(where.getFieldName()));
                columnConditionsWhere.setOperator(where.getOperator());
                columnConditionsWhere.setValue(where.getValue());
                wheres.add(columnConditionsWhere);
            }
            columnConditions.setWheres(wheres);
        }
        //排序
        if (!CollectionUtils.isEmpty(fieldConditions.getOrders())) {
            List<ColumnConditions.OrderBy> orders = new ArrayList<>(fieldConditions.getOrders().size());
            for (FieldConditions.OrderBy order : fieldConditions.getOrders()) {
                String columnName = fieldColumnMap.get(order.getFieldName());
                ColumnConditions.OrderBy orderBy = new ColumnConditions.OrderBy(columnName, order.isDesc());
                orders.add(orderBy);
            }
            columnConditions.setOrders(orders);
        }
        return columnConditions;
    }
}
