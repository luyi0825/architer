package io.github.architers.test.asynctask;

import io.github.architers.test.asynctask.annotation.AsyncTask;
import io.github.architers.test.asynctask.annotation.TaskSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
public class AsyncTaskAspect {

    private TaskSubmit taskSubmit;

    public AsyncTaskAspect(TaskSubmit taskSubmit) {
        this.taskSubmit = taskSubmit;
    }

    /**
     * caching切点
     */
    @Pointcut("@annotation(io.github.architers.test.asynctask.annotation.AsyncTask)")
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
        TaskRequestParams sendRequest = new TaskRequestParams();
        sendRequest.setTaskName(asyncTask.taskName());
        sendRequest.setArgs(jp.getArgs());
        sendRequest.setTaskId(UUID.randomUUID().toString());
        sendRequest.setExecutor(asyncTask.executor());
        sendRequest.setReliable(asyncTask.reliable());
        taskSubmit.submit(sendRequest);
        return null;
    }


    /**
     * caching切点
     */
    @Pointcut("@annotation(io.github.architers.test.asynctask.annotation.TaskSender)")
    public void taskSend() {
    }

    @Around("taskSend()")
    public Object taskSendAround(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Object result = jp.proceed();
        //添加任务
        TaskSender taskSender = method.getAnnotation(TaskSender.class);
        TaskRequestParams sendRequest = new TaskRequestParams();
        sendRequest.setTaskName(taskSender.taskName());
        sendRequest.setArgs(new Object[]{result});
        sendRequest.setTaskId(UUID.randomUUID().toString());
        sendRequest.setExecutor(taskSender.executor());
        sendRequest.setReliable(taskSender.reliable());
        taskSubmit.submit(sendRequest);
        return result;
    }

}
