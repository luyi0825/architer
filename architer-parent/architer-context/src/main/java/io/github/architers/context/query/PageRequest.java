package io.github.architers.context.query;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author luyi
 * 分页请求
 */
@Data
public class PageRequest<T> {

    /**
     * 分页信息
     */
    @NotNull(message = "分页参数不能为空")
    private PageParam pageParam;

    /**
     * 请求数据
     */
    @Valid
    @NotNull(message = "请求参数不能为空")
    private T requestParam;

}
