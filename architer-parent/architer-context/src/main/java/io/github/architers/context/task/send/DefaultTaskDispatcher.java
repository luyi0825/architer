package io.github.architers.context.task.send;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的任务分发器
 *
 * @author luyi
 */
@Component
public class DefaultTaskDispatcher implements TaskDispatcher, ApplicationContextAware {


    Map<String, TaskSender> taskSenderMap;

    @Override
    public void submit(TaskParam taskParam) {
        TaskSender taskSender = taskSenderMap.get(taskParam.getSender());
        if (taskSender == null) {
            throw new RuntimeException("TaskSender is null");
        }
        taskSender.doSend(taskParam);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TaskSender> senderMap = applicationContext.getBeansOfType(TaskSender.class);
        taskSenderMap = new HashMap<>(senderMap.size(), 1);
        senderMap.forEach((beanName, taskSender) -> {
            if (!taskSenderMap.containsKey(taskSender.senderName())) {
                taskSenderMap.put(taskSender.senderName(), taskSender);
            }
        });
    }
}
