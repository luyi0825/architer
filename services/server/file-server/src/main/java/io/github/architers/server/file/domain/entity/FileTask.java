package io.github.architers.server.file.domain.entity;

import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件任务
 *
 * @author luyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileTask extends BaseEntity {
    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 任务中文名称
     */
    private String taskCaption;

    /**
     * 任务节点类型
     *
     * @see io.github.architers.server.file.eums.FileTaskType
     */
    private Byte taskType;

    /**
     * 远程调用的地址
     */
    private String taskAddress;


}
