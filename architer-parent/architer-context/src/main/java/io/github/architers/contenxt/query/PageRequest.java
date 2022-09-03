package io.github.architers.contenxt.query;

import javax.validation.Valid;

/**
 * @author luyi
 * 分页请求
 */
public class PageRequest<T> {

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    /**
     * 请求数据
     */
    @Valid
    private T requestData;

}
