package io.github.architers.context.task.send;

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
     * @param taskParam 提交的任务参数
     */
    void submit(TaskParam taskParam);

}
