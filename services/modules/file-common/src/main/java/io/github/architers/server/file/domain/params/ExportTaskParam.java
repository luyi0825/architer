package io.github.architers.server.file.domain.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 导出任务参数
 *
 * @param <T>
 */
@Data
public class ExportTaskParam<T>  implements ExecuteTaskParam {
    /**
     * 请求记录ID
     */
    private Long recordId;

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
