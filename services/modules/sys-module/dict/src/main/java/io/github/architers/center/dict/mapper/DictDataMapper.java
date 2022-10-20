package io.github.architers.center.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;

import java.util.List;
import java.util.Set;

public interface DictDataMapper extends BaseMapper<DictData> {
    /**
     * 批量添加
     *
     * @param list 需要批量插入的数据
     * @return 插入的数量
     */
    int insertBatchSomeColumn(List<DictData> list);

    List<DictData> findByDictCodes(Integer tenantId, Set<String> dictCodes);
}
