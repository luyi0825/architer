package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.domain.entity.TaskConfig;

/**
 * 任务限制
 *
 * @author luyi
 */
public interface ITaskLimit {

    /**
     * 是否适配
     */
    boolean support(Integer target);

    /**
     * 是否已经被限制
     *
     * @param executeTaskParam
     */
    boolean canExecute(ExecuteTaskParam executeTaskParam, TaskConfig taskLimit );

    /**
     * 预备执行
     *
     * @param executeTaskParam
     * @return
     */
    void startExecute(ExecuteTaskParam executeTaskParam);

    void endExecute(ExecuteTaskParam executeTaskParam);
}
