package io.github.architers.query.dynimic;

public interface IDynamicFieldConditionInterceptor {

    DynamicColumnConditions  intercept(DynamicColumnConditions dynamicColumnConditions);
}
