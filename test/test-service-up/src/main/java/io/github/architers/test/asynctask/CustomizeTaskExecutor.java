package io.github.architers.test.asynctask;

/**
 * 定制化执行器
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
