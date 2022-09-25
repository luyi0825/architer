package io.github.architers.test.task.rocketmq;

import io.github.architers.test.task.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * rocketMq容器创造
 *
 * @author luyi
 */
@Service
@Slf4j
public class RocketMqTaskContainCreate implements ApplicationContextAware, SmartInitializingSingleton {


    @RocketMQMessageListener(topic = "test", consumerGroup = "test")
    class Test {

    }

    @Resource
    private TaskConsumerTargetRegister taskConsumerTargetRegister;

    private ApplicationContext applicationContext;

    @Resource
    private StandardEnvironment environment;

    @Resource
    private RocketMQProperties rocketProperties;

    @Resource
    private DefaultTaskExecutor defaultTaskExecutor;

    @Resource
    private RocketMQMessageConverter rocketMqMessageConverter;

    @Override

    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Collection<String> groups = taskConsumerTargetRegister.getTaskGroups();
        RocketMQMessageListener rocketMqMessageListener = Test.class.getAnnotation(RocketMQMessageListener.class);
        for (String taskGroup : groups) {
            GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
            String containerBeanName = "rocketmq-" + taskGroup + "-" + "rocketMQListenerContainer";
            genericApplicationContext.registerBean(containerBeanName, DefaultRocketMQListenerContainer.class,
                    () -> {
                        try {
                            return createRocketListenerContainer(containerBeanName, taskGroup, rocketMqMessageListener);
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

    private DefaultRocketMQListenerContainer createRocketListenerContainer(String name,
                                                                             String taskGroup,
                                                                             RocketMQMessageListener rocketMQMessageListener) throws InstantiationException, IllegalAccessException {
        DefaultRocketMQListenerContainer container = new DefaultRocketMQListenerContainer();

        container.setRocketMQMessageListener(rocketMQMessageListener);
        String accessChannel = environment.resolvePlaceholders("");
        container.setNameServer(rocketProperties.getNameServer());
        if (!StringUtils.isEmpty(accessChannel)) {
            container.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        container.setTopic(environment.resolvePlaceholders(taskGroup));
        String tags = environment.resolvePlaceholders(rocketMQMessageListener.selectorExpression());
        if (!StringUtils.isEmpty(tags)) {
            container.setSelectorExpression(tags);
        }
        container.setConsumerGroup(rocketProperties.getConsumer().getGroup());
        container.setRocketMQListener(new RocketMQListener<TimelyTaskParams>() {
            @Override
            public void onMessage(TimelyTaskParams message) {
                defaultTaskExecutor.executor(message);
            }
        });
        container.setMessageConverter(rocketMqMessageConverter.getMessageConverter());
        container.setName(name);
        return container;
    }


}
