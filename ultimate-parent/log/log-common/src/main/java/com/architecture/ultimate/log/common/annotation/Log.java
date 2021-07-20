package com.architecture.ultimate.log.common.annotation;


import com.architecture.ultimate.log.common.enums.LogType;

/**
 * @author luyi
 */
public @interface Log {
    /**
     * 是否异步
     */
    boolean async() default true;

    /**
     * 执行器:对应spring容器中的bean名称
     */
    String executor() default "";

    /**
     * 日志类型
     */
    LogType logType() default LogType.ES;

    /**
     * 日志存的schema:例如es为index,数据库为表名
     */
    String schema();

}
