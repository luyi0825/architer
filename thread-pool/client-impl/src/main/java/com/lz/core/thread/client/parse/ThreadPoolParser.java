package com.lz.core.thread.client.parse;


import com.lz.core.thread.client.annotation.ThreadPoolClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author luyi
 */
public class ThreadPoolParser implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //得到所有需要监控的线程池
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(ThreadPoolClient.class);
        map.values();
    }


}
