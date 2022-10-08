package io.github.architers.context.task.subscriber;

/**
 * 定制化任务执行器
 * <li>用于任务执行器拓展</li>
 *
 * @author luyi
 */
public interface CustomizeTaskExecutor extends TaskExecutor {

    /**
     * 得到执行器名称
     *
     * @return 执行器名称
     */
    String getExecutorName();

}
