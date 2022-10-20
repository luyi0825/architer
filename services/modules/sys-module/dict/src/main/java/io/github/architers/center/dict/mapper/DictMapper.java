package io.github.architers.center.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.dict.domain.entity.Dict;

import java.util.List;
import java.util.Set;

/**
 * @author luyi
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 批量添加
     *
     * @param list 需要批量插入的数据
     * @return 插入的数量
     */
    int insertBatchSomeColumn(List<Dict> list);

    /**
     * 通过数据字典编码删除
     *
     * @param tenantId  租户ID
     * @param dictCodes 数据字典编码
     * @return 删除的数量
     */
    int deleteByDictCode(Integer tenantId, Set<String> dictCodes);

    List<Dict> findByDictCodes(Integer tenantId, Set<String> dictCodes);
}
