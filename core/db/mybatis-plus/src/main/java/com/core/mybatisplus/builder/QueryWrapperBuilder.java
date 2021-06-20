package com.core.mybatisplus.builder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.core.mybatisplus.*;
import org.apache.commons.lang3.ArrayUtils;
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

    public QueryWrapper<T> buildQueryWrapper(QueryParams<T> queryParams) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(queryParams.getEntity());
        this.buildConditions(queryParams, queryWrapper);
        this.buildOrders(queryParams, queryWrapper);
        this.buildQueryField(queryParams, queryWrapper);
        return queryWrapper;
    }

    /**
     * 构建查询字段
     *
     * @param queryParam   查询参数
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildQueryField(QueryParams<T> queryParam, QueryWrapper<T> queryWrapper) {
        String[] fields = queryParam.getFieldName();
        if (ArrayUtils.isNotEmpty(fields)) {
            queryWrapper.select(fields);
        }

    }

    /**
     * 构建查询排序
     *
     * @param queryParams  查询参数
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildOrders(QueryParams<T> queryParams, QueryWrapper<T> queryWrapper) {
        List<Order> orderList = queryParams.getOrders();

        if (!CollectionUtils.isEmpty(orderList)) {
            orderList.forEach(order -> {
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
     * @param queryParams  查询参数
     * @param queryWrapper plus的QueryWrapper
     */
    private void buildConditions(QueryParams<T> queryParams, QueryWrapper<T> queryWrapper) {
        List<Condition> conditionList = queryParams.getConditions();
        if (!CollectionUtils.isEmpty(conditionList)) {
            conditionList.forEach(condition -> {
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
                        queryWrapper.like(field, value);
                    default:
                        break;
                }
            });
        }
    }
}
