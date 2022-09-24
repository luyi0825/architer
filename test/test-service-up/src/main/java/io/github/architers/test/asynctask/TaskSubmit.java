package io.github.architers.test.asynctask;

import java.io.Serializable;

/**
 * 异步任务执行器
 *
 * @author luyi
 */
public interface TaskSubmit extends Serializable {

    /**
     * 提交任务
     *
     * @param request
     */
    void submit(TaskSendRequest request);

}
