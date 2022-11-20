package io.github.architers.task.rocketmq;

import io.github.architers.context.task.subscriber.DefaultTaskExecutor;
import io.github.architers.context.task.subscriber.TaskSubscriberTargetScanner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

@Configuration
public class RocketTaskConfig implements ApplicationContextAware {

    @Resource
    private RocketMqSubscriberRegister rocketMqSubscriberRegister;

    @Resource
    private TaskSubscriberTargetScanner taskSubscriberTargetScanner;

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initRocketMqConsumer() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Set<String> group = taskSubscriberTargetScanner.getTaskGroups();
                rocketMqSubscriberRegister.register(applicationContext,group);
            } catch (InterruptedException e) {

            }
        }).start();
    }


    @Bean
    public RocketMqDefaultTaskSender rocketMqDefaultTaskSender() {
        return new RocketMqDefaultTaskSender();
    }

    @Bean
    public RocketMqSubscriberRegister rocketMqSubscriberRegister() {
        return new RocketMqSubscriberRegister();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
