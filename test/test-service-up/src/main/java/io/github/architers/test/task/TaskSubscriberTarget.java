package io.github.architers.test.task;


import lombok.Data;

import java.lang.reflect.Method;

/**
 * 任务消费目标对象
 *
 * @author luyi
 */
@Data
public class TaskSubscriberTarget {

    /**
     * 任务组
     */
    private String group;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务对应的bean
     */
    private Object taskBean;

    /**
     * 任务对应的方法
     */
    private Method taskMethod;

    /**
     * 任务参数
     */
    private Object[] args;


}
