package io.github.architers.context.task.proxy;


import io.github.architers.context.task.send.TaskAnnotationsParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;


/**
 * @author luyi
 * 注解缓存操作元
 */
public class AnnotationTaskSource implements TaskOperationSource {

    /**
     * 缓存解析器
     */
    private final TaskAnnotationsParser taskAnnotationsParser;

    public AnnotationTaskSource(TaskAnnotationsParser taskAnnotationsParser) {
        this.taskAnnotationsParser = taskAnnotationsParser;
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return taskAnnotationsParser.isCandidateClass(targetClass);
    }

    @Override
    public Collection<?extends Annotation> getCacheOperations(Method method, Class<?> targetClass) {
        return taskAnnotationsParser.parse(method);
    }
}
