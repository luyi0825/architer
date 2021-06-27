package com.core.mybatisplus.builder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.mybatisplus.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luyi
 * mybatis-plus的QueryWrapper构建
 */
@Component
public class QueryWrapperBuilder<T> {

    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    public QueryWrapper<T> buildQueryWrapper(QueryParam<T> queryParam) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(queryParam.getEntity());
        this.buildConditions(queryParam.getConditions(), queryWrapper);
        this.buildOrders(queryParam.getOrders(), queryWrapper);
        this.buildQueryField(null, queryWrapper);
        return queryWrapper;
    }

    /**
     * 构建查询字段
     *
     * @param fields       查询参数
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildQueryField(String[] fields, QueryWrapper<T> queryWrapper) {
        if (ArrayUtils.isNotEmpty(fields)) {
            queryWrapper.select(fields);
        }
    }

    /**
     * 构建查询排序
     *
     * @param orders       排序
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildOrders(List<Order> orders, QueryWrapper<T> queryWrapper) {
        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(order -> {
                String field = order.getField();
                OrderEnum orderEnum = order.getOrder();
                //降序
                if (OrderEnum.desc.equals(orderEnum)) {
                    queryWrapper.orderByDesc(humpToLine(field));
                } else {
                    queryWrapper.orderByAsc(humpToLine(field));
                }
            });
        }

    }

    /**
     * 驼峰转下划线
     */
    public String humpToLine(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 构建查询条件
     *
     * @param conditions   查询条件
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildConditions(List<Condition> conditions, QueryWrapper<T> queryWrapper) {
        if (!CollectionUtils.isEmpty(conditions)) {
            conditions.forEach(condition -> {
                ConditionEnum conditionEnum = condition.getCondition();
                String field = humpToLine(condition.getField());
                Object value = condition.getValue();
                if (value == null) {
                    return;
                }
                switch (conditionEnum.getOperation()) {
                    case "equal":
                        queryWrapper.eq(field, value);
                        break;
                    case "like":
                        if (ObjectUtils.isNotEmpty(value)) {
                            queryWrapper.like(field, value);
                        }

                    default:
                        break;
                }
            });
        }
    }
}
