package io.github.architers.test.task;

/**
 * 任务发送器
 *
 * @author luyi
 */
public interface TaskSender {


    /**
     * 得到处理器的名称
     *
     * @return
     */
    String processName();

    /**
     * 处理任务
     *
     * @param taskParam
     */
    void process(TaskParam taskParam);
}
