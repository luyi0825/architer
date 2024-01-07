package io.github.architers.context.lock.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;


/**
 * 缓存的 Advisor(通知）
 *
 * @author luyi
 */
public class BeanFactoryLockSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private LockOperationSource lockOperationSource;

    private LockSourcePointcut pointcut = new LockSourcePointcut() {
        @Override
        public LockOperationSource getCacheOperationSource() {
            return lockOperationSource;
        }
    };

    public BeanFactoryLockSourceAdvisor() {

    }

    public BeanFactoryLockSourceAdvisor(LockOperationSource lockOperationSource, LockSourcePointcut pointcut) {
        this.lockOperationSource = lockOperationSource;
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

    public void setCacheOperationSource(@Nullable LockOperationSource lockOperationSource) {
        this.lockOperationSource = lockOperationSource;
    }

    public void setPointcut(LockSourcePointcut pointcut) {
        this.pointcut = pointcut;
    }
}




