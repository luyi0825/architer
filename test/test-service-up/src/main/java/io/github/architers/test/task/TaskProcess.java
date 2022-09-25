package io.github.architers.test.task;

/**
 * 任务处理器
 *
 * @author luyi
 */
public interface TaskProcess {


    /**
     * 得到处理器的名称
     *
     * @return
     */
    String processName();

    /**
     * 处理任务
     *
     * @param sendParam
     */
    void process(SendParam sendParam);
}
