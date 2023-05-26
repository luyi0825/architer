package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.domain.entity.FileTaskLimitConfig;

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
    boolean canExecute(ExecuteTaskParam executeTaskParam, FileTaskLimitConfig taskLimit );

    /**
     * 预备执行
     *
     * @param executeTaskParam
     * @return
     */
    void startExecute(ExecuteTaskParam executeTaskParam);

    void endExecute(ExecuteTaskParam executeTaskParam);
}
