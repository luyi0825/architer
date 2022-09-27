package io.github.architers.test.task;

import io.github.architers.context.Symbol;
import io.github.architers.test.task.annotation.AsyncTask;
import io.github.architers.test.task.annotation.TaskSubscriber;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 任务消费目标扫描
 *
 * @author luyi
 */
public class TaskSubscriberTargetScanner implements ApplicationContextAware, SmartInitializingSingleton {

    private final AtomicBoolean init = new AtomicBoolean(false);
    /**
     * Map<group,Map<groupName,Set<TaskConsumerTarget>>>
     */
    private final ConcurrentMap<String, Map<String, Set<TaskSubscriberTarget>>> taskConsumerTargetStore = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Set<TaskSubscriberTarget>> consumerTargetStore = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    /**
     * 通过任务名称得到任务的目录对象
     *
     * @param taskName 任务名称
     * @return 任务目录集合(多个消费者)
     */
    public Set<TaskSubscriberTarget> taskConsumerTarget(String group, String taskName) {
        return consumerTargetStore.get(group + Symbol.COLON + taskName);
    }


    /**
     * 获取所有的任务名称
     */
    public Set<String> getTaskGroups() {
        if (!init.get()) {
            doScan();
        }
        return taskConsumerTargetStore.keySet();
    }


    @Override
    public void afterSingletonsInstantiated() {
        //注册异步任务消费目标
        doScan();
    }

    private synchronized void doScan() {
        if (applicationContext == null) {
            return;
        }
        //已经完成初始化
        if (init.get()) {
            return;
        }
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                TaskSubscriber taskSubscriber = AnnotationUtils.findAnnotation(method, TaskSubscriber.class);
                if (taskSubscriber != null) {
                    getAsyncTaskInfo(bean, method, taskSubscriber.taskName(), taskSubscriber.reliable(), taskSubscriber.group(), taskSubscriber.priority());
                }
                //异步任务的发送者和订阅者都是自己
                AsyncTask asyncTask = AnnotationUtils.findAnnotation(method, AsyncTask.class);
                if (asyncTask != null) {
                    getAsyncTaskInfo(bean, method, asyncTask.taskName(), asyncTask.reliable(), asyncTask.group(), asyncTask.priority());
                }
            }
        }
        taskConsumerTargetStore.forEach((group, value) -> value.forEach((taskName, consumerTargets) -> consumerTargetStore.put(group + Symbol.COLON + taskName, consumerTargets)));
        init.set(true);
    }

    private void getAsyncTaskInfo(Object bean,
                                  Method method,
                                  String taskName,
                                  boolean reliable,
                                  String group,
                                  int priority) {
        if (taskName.trim().length() == 0) {
            throw new RuntimeException("async task name invalid,for [" + bean.getClass() + "#" + method.getName() + "].");
        }
        Map<String, Set<TaskSubscriberTarget>> taskConsumerTargets = taskConsumerTargetStore.computeIfAbsent(group, k -> new HashMap<>());
        TaskSubscriberTarget taskSubscriberTarget = new TaskSubscriberTarget();
        taskSubscriberTarget.setTaskMethod(method);
        taskSubscriberTarget.setReliable(reliable);
        taskSubscriberTarget.setTaskBean(bean);
        taskSubscriberTarget.setTaskName(taskName);
        taskSubscriberTarget.setGroup(group);
        taskSubscriberTarget.setPriority(priority);
        Set<TaskSubscriberTarget> targets = taskConsumerTargets.computeIfAbsent(taskName, key -> new HashSet<>());
        targets.add(taskSubscriberTarget);


    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
