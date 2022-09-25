package io.github.architers.test.task;

import java.io.Serializable;

/**
 * 任务分发器
 *
 * @author luyi
 */
public interface TaskDispatcher extends Serializable {

    /**
     * 提交任务
     *
     * @param sendParam 提交的任务参数
     */
    void submit(SendParam sendParam);

}
