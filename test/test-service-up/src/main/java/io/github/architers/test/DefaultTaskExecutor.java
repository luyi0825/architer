package io.github.architers.test;

import io.github.architers.test.asynctask.AbstractTaskExecutor;
import io.github.architers.test.asynctask.TaskRegister;

/**
 * 默认的任务执行器
 *
 * @author luyi
 */
public class DefaultTaskExecutor extends AbstractTaskExecutor {
    public DefaultTaskExecutor(TaskRegister taskRegister) {
        super(taskRegister);
    }
}
