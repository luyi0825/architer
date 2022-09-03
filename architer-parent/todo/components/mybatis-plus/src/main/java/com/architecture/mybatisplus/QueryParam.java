package com.architecture.mybatisplus;

import io.github.architers.query.common.model.OrderCondition;
import io.github.architers.query.common.model.Pager;
import io.github.architers.query.common.model.QueryCondition;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * 查询参数
 * 分页和普通查询都可以用这个作为参数接收的对象
 */
@Data
public class QueryParam<T> implements Serializable {

    /**
     * 查询条件
     */
    private List<QueryCondition> conditions;

    /**
     * 排序条件
     */
    private List<OrderCondition> orders;

    /**
     * 分页信息
     */
    private Pager page;

    /**
     *
     */
    private T entity;

//    /**
//     * 指定的查询的字段（不指定查询所有）
//     */
//    private String[] fieldName;


}
