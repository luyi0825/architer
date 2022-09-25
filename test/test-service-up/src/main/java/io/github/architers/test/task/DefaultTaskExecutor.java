package io.github.architers.test.task;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的任务执行器
 *
 * @author luyi
 */
public class DefaultTaskExecutor extends AbstractTaskExecutor implements ApplicationContextAware {
    public DefaultTaskExecutor(TaskConsumerTargetRegister taskConsumerTargetRegister) {
        super(taskConsumerTargetRegister);
    }

    private Map<String, CustomizeTaskExecutor> customizeTaskExecutors;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CustomizeTaskExecutor> contextBeansOfTypes = applicationContext.getBeansOfType(CustomizeTaskExecutor.class);
        customizeTaskExecutors = new HashMap<>(contextBeansOfTypes.size(), 1);
        contextBeansOfTypes.forEach((beanName, instance) -> {
            customizeTaskExecutors.put(instance.getExecutorName(), instance);
        });
    }

    @Override
    public void executor(SendParam request) {
        if (customizeTaskExecutors == null || !StringUtils.hasText(request.getExecutor())) {
            super.executor(request);
            return;
        }
        CustomizeTaskExecutor customizeTaskExecutor = customizeTaskExecutors.get(request.getExecutor());
        customizeTaskExecutor.executor(request);
    }
}
