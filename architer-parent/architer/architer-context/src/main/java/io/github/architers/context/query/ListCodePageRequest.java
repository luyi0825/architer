package io.github.architers.context.query;

import lombok.Data;

/**
 * @author luyi
 * 列表分页编码分页请求参数
 */
@Data
public class ListCodePageRequest<T> {

    /**
     * 列表编码
     */
    private String listCode;

    /**
     * 分页参数
     */
    private PageParam pageParam;
}
