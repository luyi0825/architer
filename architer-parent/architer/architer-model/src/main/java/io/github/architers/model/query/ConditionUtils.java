package io.github.architers.model.query;

import org.springframework.util.CollectionUtils;

import javax.management.RuntimeErrorException;
import java.util.*;

/**
 * 查询条件工具类
 *
 * @author luyi
 * @since 1.0.3
 */
public final class ConditionUtils {

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
            List<DynamicColumnConditions.Where> wheres = buildWheres(dynamicFieldConditions.getWheres(), columnConfigMap);
            dynamicColumnConditions.setWheres(wheres);
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

    private static List<DynamicColumnConditions.Where> buildWheres(List<DynamicFieldConditions.Where> wheres, Map<String, DynamicColumnConfig> columnConfigMap) {

        if (CollectionUtils.isEmpty(wheres)) {
            return null;
        }
        List<DynamicColumnConditions.Where> whereList = new ArrayList<>(wheres.size());
        for (DynamicFieldConditions.Where where : wheres) {
            DynamicColumnConditions.Where columnConditionsWhere = new DynamicColumnConditions.Where();
            DynamicColumnConfig columnConfig = columnConfigMap.get(where.getFieldName());
            if (columnConfig == null) {
                throw new RuntimeException(String.format("列没有配置:字段【%s】", where.getFieldName()));
            }
            columnConditionsWhere.setColumnName(columnConfig.getColumnName());
            columnConditionsWhere.setOperator(where.getOperator());
            columnConditionsWhere.setValue(where.getValue());
            //TODO:抽离，区分sql
            fillWhereSql(columnConditionsWhere);
            whereList.add(columnConditionsWhere);
        }
        return whereList;

    }

    private static void fillWhereSql(DynamicColumnConditions.Where where) {
        StringBuilder whereSql = new StringBuilder();
        WhereOperator whereOperator = where.getOperator();
        whereSql.append(where.getColumnName());
        if (WhereOperator.equal.equals(whereOperator)) {
            whereSql.append(" = ");
            whereSql.append("#{whereCondition.value}");
        } else if (WhereOperator.like.equals(whereOperator)) {
            whereSql.append(" like CONCAT('%',#{whereCondition.value},'%')");
        } else if (WhereOperator.likeLeft.equals(where.getOperator())) {
            whereSql.append(" like CONCAT('%',#{whereCondition.value})");
            // whereSql.append(" like '#{whereCondition.value}%'");
        } else if (WhereOperator.likeRight.equals(where.getOperator())) {
            whereSql.append(" like CONCAT(#{whereCondition.value},'%')");
            // whereSql.append(" like '#{whereCondition.value}%'");
        } else if (WhereOperator.between.equals(where.getOperator())) {
            String[] arr = where.getValue().toString().split(",");
            where.setConvertValue(arr);
            whereSql.append(" between #{whereCondition.convertValue[0]} and #{whereCondition.convertValue[1]}");
        } else if (WhereOperator.notBetween.equals(whereOperator)) {
            String[] arr = where.getValue().toString().split(",");
            where.setConvertValue(arr);
            whereSql.append(" not between #{whereCondition.convertValue[0]} and #{whereCondition.convertValue[1]}");
        } else if (WhereOperator.in.equals(whereOperator)) {
            String[] arr = where.getValue().toString().split(",");
            where.setConvertValue((arr));
            whereSql.append(" in <script><foreach collection=\"whereCondition.convertValue\" item=\"inValue\" open=\"(\" separator=\",\" close=\")\">#{inValue}</foreach></script>");
        } else {
            throw new IllegalArgumentException("not support");
        }
        System.out.println(whereSql);
        where.setSql(whereSql.toString());
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
