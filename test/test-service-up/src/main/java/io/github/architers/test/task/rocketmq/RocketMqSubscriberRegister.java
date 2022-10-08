package io.github.architers.test.task.rocketmq;

import io.github.architers.test.task.*;
import io.github.architers.context.task.constants.ConsumerSources;
import io.github.architers.context.task.subscriber.DefaultTaskExecutor;
import io.github.architers.context.task.subscriber.TaskSubscriberRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * rocketMq订阅者注册
 *
 * @author luyi
 */
@Component
@Slf4j
public class RocketMqSubscriberRegister implements TaskSubscriberRegister {


    @Resource
    private StandardEnvironment environment;

    @Resource
    private RocketMQProperties rocketProperties;

    @Resource
    private DefaultTaskExecutor defaultTaskExecutor;

    @Resource
    private RocketMQMessageConverter rocketMqMessageConverter;


    private TaskSubscriberListenerContainer createRocketListenerContainer(String name,
                                                                           String taskGroup,
                                                                           RocketMqSubscriberExtend extend) throws InstantiationException, IllegalAccessException {
        TaskSubscriberListenerContainer  container = new TaskSubscriberListenerContainer();

        container.setRocketMqSubscriberExtend(extend);
        String accessChannel = environment.resolvePlaceholders("");
        container.setNameServer(rocketProperties.getNameServer());
        if (!StringUtils.isEmpty(accessChannel)) {
            container.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        container.setTopic(environment.resolvePlaceholders(taskGroup));
        String tags = environment.resolvePlaceholders(extend.selectorExpression());
        if (!StringUtils.isEmpty(tags)) {
            container.setSelectorExpression(tags);
        }
        container.setConsumerGroup(rocketProperties.getConsumer().getGroup());
        container.setRocketMQListener(new RocketMQListener<TaskStore>() {
            @Override
            public void onMessage(TaskStore taskStore) {
                defaultTaskExecutor.executor(taskStore);
            }
        });
        container.setMessageConverter(rocketMqMessageConverter.getMessageConverter());
        container.setName(name);
        return container;
    }

    @Override
    public ConsumerSources getSubscriberSource() {
        return ConsumerSources.ROCKET_MQ;
    }

    @Override
    public void register(ApplicationContext applicationContext, Set<String> groups) {
        for (String taskGroup : groups) {
            RocketMqSubscriberExtend extend = new RocketMqSubscriberExtend();

            GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
            String containerBeanName = "taskSubscriber-" + taskGroup + "-" + "rocketMQListenerContainer";
            genericApplicationContext.registerBean(containerBeanName, TaskSubscriberListenerContainer.class,
                    () -> {
                        try {
                            return createRocketListenerContainer(containerBeanName, taskGroup, extend);
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
            DefaultRocketMQListenerContainer container = genericApplicationContext.getBean(containerBeanName,
                    DefaultRocketMQListenerContainer.class);
            if (!container.isRunning()) {
                try {
                    container.start();
                } catch (Exception e) {
                    log.error("Started container failed. {}", container, e);
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
