package io.github.architers.query.dynimic;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 查询条件工具类
 *
 * @author luyi
 * @since 1.0.3
 */
public final class ConditionUtils {

    /**
     * 转换为动态列条件
     *
     * @param conditionCode          条件编码
     * @param dynamicFieldConditions 动态字段条件
     * @return {@link DynamicColumnConditions}
     */
    public static DynamicColumnConditions convertToColumnConditions(String conditionCode, DynamicFieldConditions dynamicFieldConditions) {

        //查询的列
        Map<String/*字段名*/, DynamicColumnConfig> columnConfigMap = DynamicConditionManager.getMapByCode(conditionCode);
        if (columnConfigMap == null) {
            throw new RuntimeException("动态列的配置不存在");
        }
        DynamicColumnConditions dynamicColumnConditions = new DynamicColumnConditions();

        {//构建查询列
            Set<String> fieldNames = dynamicFieldConditions.getFieldNames();
            List<DynamicColumnConditions.Column> columns = buildQueryColumns(fieldNames, columnConfigMap);
            dynamicColumnConditions.setColumns(columns);
        }

        {
            //where条件
            if (!CollectionUtils.isEmpty(dynamicFieldConditions.getWheres())) {
                fillWhereInfo(dynamicFieldConditions.getWheres(), columnConfigMap);
            }
            dynamicColumnConditions.setWheres(dynamicFieldConditions.getWheres());
        }
        {
            //排序
            List<DynamicColumnConditions.OrderBy> orders = buildOrders(dynamicFieldConditions.getOrders(), columnConfigMap);
            dynamicColumnConditions.setOrders(orders);
        }

        return dynamicColumnConditions;
    }

    private static List<DynamicColumnConditions.OrderBy> buildOrders(List<DynamicFieldConditions.OrderBy> orders, Map<String, DynamicColumnConfig> columnConfigMap) {
        if (CollectionUtils.isEmpty(orders)) {
            return null;
        }
        List<DynamicColumnConditions.OrderBy> orderByList = new ArrayList<>(orders.size());
        for (DynamicFieldConditions.OrderBy order : orders) {
            DynamicColumnConfig dynamicColumnConfig = columnConfigMap.get(order.getFieldName());
            DynamicColumnConditions.OrderBy orderBy = new DynamicColumnConditions.OrderBy(dynamicColumnConfig.getColumnName(), order.isDesc());
            orderByList.add(orderBy);
        }
        return orderByList;
    }


    private static void fillWhereInfo(List<Where> wheres, Map<String, DynamicColumnConfig> columnConfigMap) {
        for (Where where : wheres) {
            where.getWhereConditions().forEach(columnWhereCondition -> {
                DynamicColumnConfig columnConfig = columnConfigMap.get(columnWhereCondition.getFieldName());
                columnWhereCondition.setColumnName(columnConfig.getColumnName());
            });
            if (!CollectionUtils.isEmpty(where.getWheres())) {
                fillWhereInfo(where.getWheres(), columnConfigMap);
            }
        }
    }

    /**
     * @param fieldNames      字段名
     * @param columnConfigMap 列配置
     */
    private static List<DynamicColumnConditions.Column> buildQueryColumns(Set<String> fieldNames, Map<String, DynamicColumnConfig> columnConfigMap) {
        List<DynamicColumnConditions.Column> columns = new ArrayList<>(fieldNames.size());
        //查询列
        for (String fieldName : fieldNames) {
            DynamicColumnConfig dynamicColumnConfig = columnConfigMap.get(fieldName);
            if (dynamicColumnConfig == null) {
                throw new RuntimeException(String.format("列没有配置:字段【%s】", fieldName));
            }
            DynamicColumnConditions.Column column = new DynamicColumnConditions.Column();
            column.setColumnName(dynamicColumnConfig.getColumnName());
            column.setColumnAlias(dynamicColumnConfig.getRealColumnAlias());
            columns.add(column);
        }
        return columns;
    }
}
