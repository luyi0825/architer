package io.github.architers.dynamiccolumn.mapper;

import io.github.architers.query.dynimic.DynamicColumnConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicColumnMapper {

    List<Map<String, Object>> getDynamicList(@Param("dynamicCondition") DynamicColumnConditions dynamicColumnConditions);

}
