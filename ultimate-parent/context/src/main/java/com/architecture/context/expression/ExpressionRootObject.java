package com.architecture.context.expression;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author luyi
 * 缓存表达式的根对象（spEl支持的语法都在这个里边）
 */
public class ExpressionRootObject {
    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;

    public ExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.args = args;
        this.target = target;
        this.targetClass = targetClass;
    }

    /**
     * 获取指定变量字段的值
     *
     * @param fieldName 字段名称
     * @return 变量的值
     * @throws NoSuchFieldException   没有这个字段
     * @throws IllegalAccessException 不能访问
     */
    public Object getVariable(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = this.getTargetClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(this.target);
    }

    /**
     * 获取指定的方法
     */
    public Method getMethod(String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return this.targetClass.getMethod(methodName, parameterTypes);
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    /**
     * 得到方法名称
     */
    public String getMethodName() {
        return this.method.getName();
    }

    public Object getTarget() {
        return target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }


}
