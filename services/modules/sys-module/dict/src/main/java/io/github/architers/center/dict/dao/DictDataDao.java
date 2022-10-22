package io.github.architers.center.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;
import io.github.architers.component.mybatisplus.InsertBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DictDataDao extends BaseMapper<DictData>, InsertBatch<DictData> {

    List<DictData> findByDictCodes(@Param("tenantId") Integer tenantId,
                                   @Param("dictCodes") Set<String> dictCodes);
}
