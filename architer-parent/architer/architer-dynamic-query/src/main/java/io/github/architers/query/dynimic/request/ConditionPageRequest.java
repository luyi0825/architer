package io.github.architers.query.dynimic.request;

import io.github.architers.common.model.request.PageRequest;
import io.github.architers.query.dynimic.DynamicFieldConditions;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带条件的分页参数请求
 *
 * @author luyi
 * @since 1.0.3
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ConditionPageRequest<T> extends PageRequest<T> {

    /**
     * 根据这个code,对conditions进行转换(拓转字段)
     */
    private String conditionCode;

    /**
     * 查询的字段条件
     */
    private DynamicFieldConditions dynamicFieldConditions;
}
