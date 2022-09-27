package io.github.architers.test.task;

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


    Map<String, TaskSender> taskProcessMap;

    @Override
    public void submit(TaskParam taskParam) {
        TaskSender taskSender = taskProcessMap.get(taskParam.getProcessName());
        taskSender.process(taskParam);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TaskSender> processMap = applicationContext.getBeansOfType(TaskSender.class);
        taskProcessMap = new HashMap<>(processMap.size(), 1);
        processMap.forEach((beanName, taskSender) -> {
            if (!processMap.containsKey(taskSender.processName())) {
                taskProcessMap.put(taskSender.processName(), taskSender);
            }
        });
    }
}
