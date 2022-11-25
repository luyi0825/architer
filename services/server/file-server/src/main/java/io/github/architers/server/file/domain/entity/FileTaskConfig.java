package io.github.architers.server.file.domain.entity;

import lombok.Data;

/**
 * 任务下载配置
 *
 * @author luyi
 */
@Data
public class FileTaskConfig {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 限制对象
     */
    private Integer limitTarget;

    /**
     * 限制时间(秒）
     */
    private Long limitSecond;

    /**
     * 限制数量
     */
    private Integer limitCount;

    /**
     * 限制策略
     */
    private String limitPolicy;

    /**
     * 任务执行器
     */
    private Integer taskExecutor;
}
