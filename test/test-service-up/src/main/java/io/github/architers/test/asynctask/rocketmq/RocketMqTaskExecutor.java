package io.github.architers.test.asynctask.rocketmq;

import io.github.architers.test.asynctask.TaskSendRequest;
import io.github.architers.test.asynctask.TaskSubmit;
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
public class RocketMqTaskExecutor implements TaskSubmit {

    @Resource
    private RocketMQTemplate rocketMqTemplate;


    @Override
    public void submit(TaskSendRequest request) {
        if (request.isReliable()) {
            //发送可靠信息
            SendResult sendResult = rocketMqTemplate.syncSend(request.getTaskName(), request);
            if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                throw new RuntimeException("发送异步任务失败");
            }
            return;
        }
        rocketMqTemplate.sendOneWay(request.getTaskName(), request);
    }


}
