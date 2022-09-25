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


    Map<String, TaskProcess> taskProcessMap;

    @Override
    public void submit(SendParam sendParam) {
        TaskProcess taskProcess = taskProcessMap.get(sendParam.getProcessName());
        taskProcess.process(sendParam);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, TaskProcess> processMap = applicationContext.getBeansOfType(TaskProcess.class);
        taskProcessMap = new HashMap<>(processMap.size(), 1);
        processMap.forEach((beanName, taskProcess) -> {
            if (!processMap.containsKey(taskProcess.processName())) {
                taskProcessMap.put(taskProcess.processName(), taskProcess);
            }
        });
    }
}
