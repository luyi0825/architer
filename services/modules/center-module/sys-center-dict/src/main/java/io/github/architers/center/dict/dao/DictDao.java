package io.github.architers.center.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.dict.domain.dto.ExportDictDTO;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.component.mybatisplus.InsertBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author luyi
 */
@Mapper
public interface DictDao extends BaseMapper<Dict>, InsertBatch<Dict> {


    /**
     * 通过数据字典编码删除
     *
     * @param tenantId  租户ID
     * @param dictCodes 数据字典编码
     * @return 删除的数量
     */
    int deleteByDictCode(@Param("tenantId") Integer tenantId,
                         @Param("dictCodes") Set<String> dictCodes);

    List<Dict> findByDictCodes(@Param("tenantId") Integer tenantId,
                               @Param("dictCodes") Set<String> dictCodes);

    int countByDictCode(@Param("tenantId") Integer tenantId,
                        @Param("dictCode") String dictCode);

    /**
     * 查询导出字典的数据
     *
     * @param exportDictDTO 导出的参数
     * @return 需要导出的字典数据
     */
    List<Dict> selectForExportDict(ExportDictDTO exportDictDTO);
}
