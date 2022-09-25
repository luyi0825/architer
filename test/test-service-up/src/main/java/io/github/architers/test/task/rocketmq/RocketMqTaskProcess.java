package io.github.architers.test.task.rocketmq;

import io.github.architers.test.task.SendParam;
import io.github.architers.test.task.TaskProcess;
import io.github.architers.test.task.constants.ProcessName;
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
public class RocketMqTaskProcess implements TaskProcess {

    @Resource
    private RocketMQTemplate rocketMqTemplate;


    @Override
    public String processName() {
        return ProcessName.ROCKET_MQ;
    }

    @Override
    public void process(SendParam sendParam) {
        if (sendParam.isReliable()) {
            //发送可靠信息
            SendResult sendResult = rocketMqTemplate.syncSend(sendParam.getGroup(), sendParam);
            if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
                throw new RuntimeException("发送异步任务失败");
            }
            return;
        }
        rocketMqTemplate.sendOneWay(sendParam.getGroup(), sendParam);
    }
}
