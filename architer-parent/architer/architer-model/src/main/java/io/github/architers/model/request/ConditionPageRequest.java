package io.github.architers.model.request;

import io.github.architers.model.query.FieldConditions;
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
public class ConditionPageRequest<T> extends PaginationRequest<T> {

    /**
     * 根据这个code,对conditions进行转换(拓转字段)
     */
    private String conditionCode;

    /**
     * 查询的字段条件
     */
    private FieldConditions fieldConditions;
}
