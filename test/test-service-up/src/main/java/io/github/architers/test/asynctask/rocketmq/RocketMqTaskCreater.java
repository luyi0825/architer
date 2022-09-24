package io.github.architers.test.asynctask.rocketmq;

import io.github.architers.test.asynctask.*;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;

@Service
@Slf4j
public class RocketMqTaskCreater implements ApplicationContextAware, SmartInitializingSingleton {


    @RocketMQMessageListener(topic = "test", consumerGroup = "test")
    class Test {

    }

    @Resource
    private TaskRegister taskRegister;

    private ApplicationContext applicationContext;

    @Resource
    private StandardEnvironment environment;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private TaskSubmit taskSubmit;

    @Resource
    private RocketMQMessageConverter rocketMQMessageConverter;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {


        Collection<String> taskNames = taskRegister.getTaskNames();
        RocketMQMessageListener rocketMQMessageListener = Test.class.getAnnotation(RocketMQMessageListener.class);
        for (String taskName : taskNames) {
            GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
            String containerBeanName = "rocketmq-" + taskName+"-"+"rocketMQListenerContainer";
            genericApplicationContext.registerBean(containerBeanName, DefaultRocketMQListenerContainer.class,
                    () -> {
                        try {
                            return createRocketMQListenerContainer(containerBeanName, taskName, rocketMQMessageListener);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
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

    private DefaultRocketMQListenerContainer createRocketMQListenerContainer(String name,
                                                                             String taskName ,
                                                                             RocketMQMessageListener rocketMQMessageListener) throws InstantiationException, IllegalAccessException {
        DefaultRocketMQListenerContainer container = new DefaultRocketMQListenerContainer();

        container.setRocketMQMessageListener(rocketMQMessageListener);

        //  String nameServer = environment.resolvePlaceholders(annotation.nameServer());
        // nameServer = rocketMQProperties.getNameServer();
        String accessChannel = environment.resolvePlaceholders("");
        container.setNameServer(rocketMQProperties.getNameServer());
        if (!StringUtils.isEmpty(accessChannel)) {
            container.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        container.setTopic(environment.resolvePlaceholders(taskName));
        String tags = environment.resolvePlaceholders(rocketMQMessageListener.selectorExpression());
        if (!StringUtils.isEmpty(tags)) {
            container.setSelectorExpression(tags);
        }
        container.setConsumerGroup("test");
        container.setRocketMQListener(new RocketMQListener<TaskRequest>() {
            @Override
            public void onMessage(TaskRequest message) {
                taskSubmit.executor(message);
            }
        });
        container.setMessageConverter(rocketMQMessageConverter.getMessageConverter());
        container.setName(name);
        return container;
    }


}
