package com.core.mybatisplus.service;

import com.core.mybatisplus.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Service接口层对应的基类
 *
 * @author luyi
 */
public interface BaseService<T> {
    /**
     * 分页查询
     *
     * @param queryParam 查询条件
     * @return 分页信息
     */
    Pagination pageQuery(QueryParam<T> queryParam);


    /**
     * 描述：根据指定查询条件查询
     *
     * @param queryParam 查询参数
     * @return list 数据
     * @date 2020/12/28
     */
    @Autowired
    List<T> queryList(QueryParam<T> queryParam);


}
