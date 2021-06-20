package com.core.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.mybatisplus.Pagination;
import com.core.mybatisplus.QueryParams;
import com.core.mybatisplus.builder.IPageBuilder;
import com.core.mybatisplus.builder.QueryWrapperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luyi
 * service 基础实现类
 * 注意，使用公共方法的时候，会在QueryWrapperBuilder自动将驼峰转下划线形式---数据库字段统一定义成下划线
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private QueryWrapperBuilder<T> queryWrapperBuilder;
    /**
     * 分页查询
     *
     * @param queryParams 查询参数
     * @return 分页信息
     */
    public Pagination pageQuery(QueryParams<T> queryParams) {
        //分页对象
        IPage<T> page = this.page(IPageBuilder.buildIPage(queryParams), queryWrapperBuilder.buildQueryWrapper(queryParams));
        return new Pagination(page);
    }

    /**
     * 描述：根据指定查询条件查询
     *
     * @param queryParams 查询参数
     * @return list 数据
     * @date 2020/12/28
     */
    public List<T> queryList(QueryParams<T> queryParams) {
        return this.getBaseMapper().selectList(queryWrapperBuilder.buildQueryWrapper(queryParams));
    }

    @Autowired
    public void setQueryWrapperBuilder(QueryWrapperBuilder queryWrapperBuilder) {
        this.queryWrapperBuilder = queryWrapperBuilder;
    }
}
