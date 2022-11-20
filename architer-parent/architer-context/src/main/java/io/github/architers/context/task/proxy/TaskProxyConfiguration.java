package io.github.architers.context.task.proxy;


import io.github.architers.context.cache.CacheAnnotationsParser;
import io.github.architers.context.task.send.DefaultTaskDispatcher;
import io.github.architers.context.task.send.TaskAnnotationsParser;
import io.github.architers.context.task.send.TaskDispatcher;
import io.github.architers.context.task.subscriber.DefaultTaskExecutor;
import io.github.architers.context.task.subscriber.TaskSubscriberTargetScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author luyi
 * 缓存代理配置类
 **/
@Configuration
public class TaskProxyConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TaskAnnotationsParser taskAnnotationsParser() {
        return new TaskAnnotationsParser();
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotationTaskSource annotationTaskSource(TaskAnnotationsParser taskAnnotationsParser) {
        return new AnnotationTaskSource(taskAnnotationsParser);
    }

    @Bean
    public TaskDispatcher taskDispatcher() {
        return new DefaultTaskDispatcher();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TaskInterceptor taskInterceptor(TaskAnnotationsParser taskAnnotationsParser,
                                           TaskDispatcher taskDispatcher) {
        TaskInterceptor taskInterceptor = new TaskInterceptor();
        taskInterceptor.setTaskDispatcher(taskDispatcher);
        taskInterceptor.setTaskAnnotationsParser(taskAnnotationsParser);
        return taskInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryTaskSourceAdvisor beanFactoryTaskSourceAdvisor(AnnotationTaskSource annotationTaskSource,
                                                                     TaskInterceptor taskInterceptor) {
        BeanFactoryTaskSourceAdvisor advisor = new BeanFactoryTaskSourceAdvisor();
        advisor.setCacheOperationSource(annotationTaskSource);
        advisor.setAdvice(taskInterceptor);
        return advisor;
    }

    @Bean
    public TaskSubscriberTargetScanner taskSubscriberTargetScanner() {
        return new TaskSubscriberTargetScanner();
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultTaskExecutor defaultTaskExecutor(TaskSubscriberTargetScanner taskSubscriberTargetScanner) {
        return new DefaultTaskExecutor(taskSubscriberTargetScanner);
    }
}
