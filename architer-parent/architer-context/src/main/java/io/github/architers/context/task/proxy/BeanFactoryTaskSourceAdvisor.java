package io.github.architers.context.task.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;


/**
 * 缓存的 Advisor(通知）
 *
 * @author luyi
 */
public class BeanFactoryTaskSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private TaskOperationSource taskOperationSource;

    private TaskSourcePointcut pointcut = new TaskSourcePointcut() {
        @Override
        public TaskOperationSource getCacheOperationSource() {
            return taskOperationSource;
        }
    };

    public BeanFactoryTaskSourceAdvisor() {

    }

    public BeanFactoryTaskSourceAdvisor(TaskOperationSource taskOperationSource, TaskSourcePointcut pointcut) {
        this.taskOperationSource = taskOperationSource;
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    /**
     * 设置切点类过滤器
     *
     * @param classFilter 类过滤器
     */
    public void setClassFilter(ClassFilter classFilter) {
        this.pointcut.setClassFilter(classFilter);
    }

    public void setCacheOperationSource(@Nullable TaskOperationSource taskOperationSource) {
        this.taskOperationSource = taskOperationSource;
    }

    public void setPointcut(TaskSourcePointcut pointcut) {
        this.pointcut = pointcut;
    }
}




