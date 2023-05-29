package io.github.architers.server.file.domain.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 导出任务参数
 *
 * @param <T>
 */
@Data
public class ImportTaskParam<T> implements ExecuteTaskParam {
    /**
     * 任务请求ID
     */
    private String requestId;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 导出人
     */
    private Long exportUserId;

    /**
     * 请求参数
     */
    private T requestBody;


}
