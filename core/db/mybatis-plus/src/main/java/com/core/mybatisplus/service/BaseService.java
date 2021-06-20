package com.core.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.mybatisplus.Pagination;
import com.core.mybatisplus.QueryParams;


/**
 * Service接口层对应的基类
 *
 * @author luyi
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 分页查询
     *
     * @param queryParams 查询参数
     * @return 分页信息
     */
    Pagination pageQuery(QueryParams<T> queryParams);


}
