package com.architecture.ultimate.mybatisplus.service;

import com.architecture.ultimate.mybatisplus.QueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.architecture.ultimate.query.common.model.Pagination;
import org.springframework.lang.NonNull;

import java.io.Serializable;
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
    List<T> selectList(QueryParam<T> queryParam);

    /**
     * 通过mybitis-plus的QueryWrapper查询
     *
     * @param queryWrapper mybitis-plus的QueryWrapper
     * @return 满足条件的结果集合
     */
    List<T> selectList(QueryWrapper<T> queryWrapper);

    /**
     * 通过主键查询
     *
     * @param id 主键ID
     * @return 实体信息
     */
    T getById(@NonNull Serializable id);

    /**
     * 添加
     *
     * @param entity 需要添加的信息
     * @return 添加成功的条数
     */
    int insert(T entity);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 删除的条数
     */
    int delete(Serializable id);

    /**
     * 查询一条数据
     *
     * @param queryWrapper mybitis-plus的QueryWrapper
     * @return 单个数据
     */
    T selectOne(QueryWrapper<T> queryWrapper);

    /**
     * 更新
     *
     * @param entity 更新的实体信息
     * @return 更新影响的数量
     */
    int updateById(T entity);
}
