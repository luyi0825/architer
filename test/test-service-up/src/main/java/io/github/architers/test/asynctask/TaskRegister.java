package io.github.architers.test.asynctask;

import io.github.architers.test.asynctask.annotation.AsyncTask;
import io.github.architers.test.asynctask.annotation.TaskConsumer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 任务注册器
 *
 * @author luyi
 */
public class TaskRegister implements ApplicationContextAware, SmartInitializingSingleton {

    private final AtomicBoolean init = new AtomicBoolean(false);
    private final ConcurrentMap<String, Set<TaskConsumerTarget>> taskConsumerTargetStore = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    public Set<TaskConsumerTarget> taskConsumerTarget(String taskName) {
        return taskConsumerTargetStore.get(taskName);
    }


    public Collection<String> getTaskNames() {
        if (!init.get()) {
            initAsyncTaskInfos();
        }
        return taskConsumerTargetStore.keySet();
    }


    public Set<String> getTaskName() {
        return taskConsumerTargetStore.keySet();
    }

    @Override
    public void afterSingletonsInstantiated() {
        //注册异步任务
        initAsyncTaskInfos();
    }

    private void initAsyncTaskInfos() {
        if (applicationContext == null) {
            return;
        }
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                TaskConsumer taskConsumer = AnnotationUtils.findAnnotation(method, TaskConsumer.class);
                if (taskConsumer != null) {
                    getAsyncTaskInfo(bean, method, taskConsumer.taskName(), taskConsumer.reliable(), taskConsumer.group(), taskConsumer.priority());
                }
                AsyncTask asyncTask = AnnotationUtils.findAnnotation(method, AsyncTask.class);
                if (asyncTask != null) {
                    getAsyncTaskInfo(bean, method, asyncTask.taskName(), asyncTask.reliable(), asyncTask.group(), asyncTask.priority());
                }
            }

            init.set(true);
        }
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
        Set<TaskConsumerTarget> taskConsumerTargets = taskConsumerTargetStore.computeIfAbsent(taskName, k -> new HashSet<>());
        // throw new RuntimeException("async task name conflicts,for [" + bean.getClass() + "#" + method.getName() + "].");
        TaskConsumerTarget taskConsumerTarget = new TaskConsumerTarget();
        taskConsumerTarget.setTaskMethod(method);
        taskConsumerTarget.setReliable(reliable);
        taskConsumerTarget.setTaskBean(bean);
        taskConsumerTarget.setTaskName(taskName);
        taskConsumerTarget.setGroup(group);
        taskConsumerTarget.setPriority(priority);
        taskConsumerTargets.add(taskConsumerTarget);


    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
