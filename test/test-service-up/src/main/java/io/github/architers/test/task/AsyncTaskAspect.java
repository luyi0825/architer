package io.github.architers.test.task;

import io.github.architers.test.task.annotation.AsyncTask;
import io.github.architers.test.task.annotation.TaskSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
public class AsyncTaskAspect {

    private TaskDispatcher taskDispatcher;

    public AsyncTaskAspect(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
    }

    /**
     * caching切点
     */
    @Pointcut("@annotation(io.github.architers.test.task.annotation.AsyncTask)")
    public void asyncTask() {
    }

    @Around("asyncTask()")
    public Object putCaching(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        //执行任务
        if (AsyncTaskContext.isExecutor()) {
            return jp.proceed();
        }
        //添加任务
        AsyncTask asyncTask = method.getAnnotation(AsyncTask.class);
        TimelyTaskParams sendRequest = new TimelyTaskParams();
        sendRequest.setTaskName(asyncTask.taskName());
        sendRequest.setArgs(jp.getArgs());
        sendRequest.setTaskId(UUID.randomUUID().toString());
        sendRequest.setExecutor(asyncTask.executor());
        sendRequest.setReliable(asyncTask.reliable());
        taskDispatcher.submit(sendRequest);
        return null;
    }


    /**
     * caching切点
     */
    @Pointcut("@annotation(io.github.architers.test.task.annotation.TaskSender)")
    public void taskSend() {
    }

    @Around("taskSend()")
    public Object taskSendAround(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Object result = jp.proceed();
        //添加任务
        TaskSender taskSender = method.getAnnotation(TaskSender.class);
        if (taskSender.delayedTime() > 0) {
            DelayedTaskParam delayedTaskParam = new DelayedTaskParam();
            delayedTaskParam.setTaskName(taskSender.taskName());
            delayedTaskParam.setArgs(new Object[]{result});
            delayedTaskParam.setTaskId(UUID.randomUUID().toString());
            delayedTaskParam.setExecutor(taskSender.executor());
            delayedTaskParam.setReliable(taskSender.reliable());
            delayedTaskParam.setDelayedTime(taskSender.delayedTime());
            delayedTaskParam.setTimeUnit(taskSender.timeUnit());
            delayedTaskParam.setProcessName(taskSender.process());
            delayedTaskParam.setGroup(taskSender.group());
            taskDispatcher.submit(delayedTaskParam);
            return result;
        }
        TimelyTaskParams sendRequest = new TimelyTaskParams();
        sendRequest.setTaskName(taskSender.taskName());
        sendRequest.setArgs(new Object[]{result});
        sendRequest.setTaskId(UUID.randomUUID().toString());
        sendRequest.setExecutor(taskSender.executor());
        sendRequest.setReliable(taskSender.reliable());
        sendRequest.setProcessName(taskSender.process());
        sendRequest.setGroup(taskSender.group());
        taskDispatcher.submit(sendRequest);
        return result;
    }

}
