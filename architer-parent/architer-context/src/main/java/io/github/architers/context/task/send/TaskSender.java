package io.github.architers.context.task.send;

/**
 * 任务发送器
 *
 * @author luyi
 */
public interface TaskSender {


    /**
     * 得到发送器的名称
     *
     * @return
     */
    String senderName();

    /**
     * 处理任务
     *
     * @param taskParam
     */
    void doSend(TaskParam taskParam);
}
