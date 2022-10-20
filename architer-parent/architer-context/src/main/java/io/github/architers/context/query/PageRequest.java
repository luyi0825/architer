package io.github.architers.context.query;

import lombok.Data;

import javax.validation.Valid;

/**
 * @author luyi
 * 分页请求
 */
@Data
public class PageRequest<T> {

    /**
     * 分页信息
     */
    private PageParam pageParam;

    /**
     * 请求数据
     */
    @Valid
    private T requestParam;

}
