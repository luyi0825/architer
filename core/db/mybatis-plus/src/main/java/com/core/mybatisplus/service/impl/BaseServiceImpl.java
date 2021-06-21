package com.core.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.mybatisplus.*;
import com.core.mybatisplus.builder.QueryWrapperBuilder;
import com.core.mybatisplus.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luyi
 * service 基础实现类
 * 注意，使用公共方法的时候，会在QueryWrapperBuilder自动将驼峰转下划线形式---数据库字段统一定义成下划线
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private QueryWrapperBuilder<T> queryWrapperBuilder;
    protected BaseMapper<T> baseMapper;

    @Override
    public Pagination pageQuery(QueryParam<T> queryParam) {
        //分页对象
        Pager pager = queryParam.getPage();
        if (pager == null) {
            pager = new Pager();
        }
        Page page = new Page<T>(pager.getCurrentPage(), pager.getLimit());
        page = this.baseMapper.selectPage(page, queryWrapperBuilder.buildQueryWrapper(queryParam));
        return new Pagination(page);
    }


    /**
     * 描述：根据指定查询条件查询
     *
     * @param queryParam 查询参数
     * @return list 数据
     * @date 2020/12/28
     */
    @Override
    public List<T> queryList(QueryParam<T> queryParam) {
        return this.baseMapper.selectList(queryWrapperBuilder.buildQueryWrapper(queryParam));
    }

    @Autowired
    public void setQueryWrapperBuilder(QueryWrapperBuilder<T> queryWrapperBuilder) {
        this.queryWrapperBuilder = queryWrapperBuilder;
    }

    @Autowired
    public void setBaseMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }
}
