package io.github.architers.test.task.pulsar;

import io.github.architers.context.task.send.TaskParam;
import io.github.architers.context.task.send.TaskSender;
import io.github.architers.context.task.constants.SenderName;

/**
 * @author luyi
 */
public class PulsarTaskSender implements TaskSender {
    @Override
    public String senderName() {
        return SenderName.PULSAR;
    }

    @Override
    public void doSend(TaskParam taskParam) {

    }
}
