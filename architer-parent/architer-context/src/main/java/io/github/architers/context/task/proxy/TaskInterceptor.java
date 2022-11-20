package io.github.architers.context.task.proxy;


import io.github.architers.context.NullValue;
import io.github.architers.context.cache.CacheAnnotationsParser;
import io.github.architers.context.cache.operation.CacheOperationHandler;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.expression.ExpressionParser;
import io.github.architers.context.task.annotation.AsyncTask;
import io.github.architers.context.task.send.TaskAnnotationsParser;
import io.github.architers.context.task.send.TaskDispatcher;
import io.github.architers.context.task.send.TaskParam;
import io.github.architers.context.task.send.TimelyTaskParams;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class TaskInterceptor implements MethodInterceptor {

    private TaskAnnotationsParser taskAnnotationsParser;

    private TaskDispatcher taskDispatcher;

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        // TaskParam taskParam = new
        Collection<? extends Annotation> annotations = taskAnnotationsParser.parse(invocation.getMethod());
        for (Annotation annotation : annotations) {
            if (annotation instanceof AsyncTask) {
                AsyncTask asyncTask = (AsyncTask) annotation;
                TimelyTaskParams timelyTaskParam = new TimelyTaskParams();
                timelyTaskParam.setGroup(asyncTask.group());
                timelyTaskParam.setSender(asyncTask.sender());
                timelyTaskParam.setExecutor(asyncTask.executor());
                timelyTaskParam.setArgs(invocation.getArguments());
                timelyTaskParam.setTaskName(asyncTask.taskName());
                taskDispatcher.submit(timelyTaskParam);
                return null;
            }
        }
        return null;

    }


    /**
     * 构建缓存的表达式元数据
     *
     * @param invocation 方法代理的信息
     * @return 表达式元数据
     */
    private ExpressionMetadata buildExpressionMeta(MethodInvocation invocation) {
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(invocation.getThis()), invocation.getMethod(), invocation.getArguments());
        MethodBasedEvaluationContext expressionEvaluationContext = ExpressionParser.createEvaluationContext(expressionMetadata);
        expressionMetadata.setEvaluationContext(expressionEvaluationContext);
        return expressionMetadata;
    }


    public void setTaskAnnotationsParser(TaskAnnotationsParser taskAnnotationsParser) {
        this.taskAnnotationsParser = taskAnnotationsParser;
    }


    public TaskInterceptor setTaskDispatcher(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
        return this;
    }
}
