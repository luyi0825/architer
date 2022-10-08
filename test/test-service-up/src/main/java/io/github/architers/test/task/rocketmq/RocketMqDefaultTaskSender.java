package io.github.architers.test.task.rocketmq;

import io.github.architers.context.task.TaskStore;
import io.github.architers.context.task.send.TaskParam;
import io.github.architers.context.task.send.TaskSender;
import io.github.architers.context.task.constants.SenderName;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * rocketMq任务执行器
 *
 * @author luyi
 */
@Component
public class RocketMqDefaultTaskSender implements TaskSender {

    @Resource
    private RocketMQTemplate rocketMqTemplate;



    @Override
    public String senderName() {
        return SenderName.ROCKET_MQ;
    }

    @Override
    public void doSend(TaskParam taskParam) {
        TaskStore taskStore = taskParam.convert2TaskStore();
        RocketMqSenderExtend rocketMqSenderExtend = (RocketMqSenderExtend) taskParam.getSenderExtend();
        if (rocketMqSenderExtend == null) {
            if (taskParam.isReliable()) {
                //发送可靠信息
                SendResult sendResult = rocketMqTemplate.syncSend(taskParam.getGroup(), taskStore);
                if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                    throw new RuntimeException("发送异步任务失败");
                }
                return;
            }
            rocketMqTemplate.sendOneWay(taskParam.getGroup(), taskStore);
        }
    }
}
