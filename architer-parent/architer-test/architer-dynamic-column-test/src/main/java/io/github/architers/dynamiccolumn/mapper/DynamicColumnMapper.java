package io.github.architers.dynamiccolumn.mapper;

import io.github.architers.model.query.DynamicColumnConditions;
import io.github.architers.model.request.ConditionPageRequest;

import java.util.List;
import java.util.Map;

public interface DynamicColumnMapper {

    List<Map<String, Object>> getDynamicList(DynamicColumnConditions dynamicColumnConditions);

}
