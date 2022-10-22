package io.github.architers.component.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

public interface InsertBatch<T> extends BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param list 插入的数据
     * @return 插入的数量
     */
    int insertBatch(Collection<T> list);

}
