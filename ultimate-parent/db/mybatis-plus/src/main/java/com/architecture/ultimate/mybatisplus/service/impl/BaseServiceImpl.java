package com.architecture.ultimate.mybatisplus.service.impl;

import com.architecture.ultimate.mybatisplus.QueryParam;
import com.architecture.ultimate.mybatisplus.builder.QueryWrapperBuilder;
import com.architecture.ultimate.mybatisplus.service.BaseService;
import com.architecture.ultimate.query.common.model.Pager;
import com.architecture.ultimate.query.common.model.Pagination;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.io.Serializable;
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
    public Pagination<T> pageQuery(QueryParam<T> queryParam) {
        //分页对象
        Pager pager = queryParam.getPage();
        if (pager == null) {
            pager = new Pager();
        }
        Page<T> page = new Page<>(pager.getCurrentPage(), pager.getLimit());
        page = this.baseMapper.selectPage(page, queryWrapperBuilder.buildQueryWrapper(queryParam));
        return new Pagination<T>(page.getRecords(),page.getTotal(),(int)page.getSize(),(int)page.getCurrent());
    }

    @Override
    public List<T> selectList(QueryParam<T> queryParam) {
        return this.baseMapper.selectList(queryWrapperBuilder.buildQueryWrapper(queryParam));
    }

    @Override
    public List<T> selectList(QueryWrapper<T> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public T getById(@NonNull Serializable id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public int insert(T entity) {
        return this.baseMapper.insert(entity);
    }

    @Override
    public int delete(Serializable id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public T selectOne(QueryWrapper<T> queryWrapper) {
        return this.baseMapper.selectOne(queryWrapper);
    }


    @Override
    public int updateById(T entity) {
        return this.baseMapper.updateById(entity);
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
