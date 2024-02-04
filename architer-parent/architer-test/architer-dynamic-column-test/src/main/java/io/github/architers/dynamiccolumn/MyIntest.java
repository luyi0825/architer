package io.github.architers.dynamiccolumn;

import io.github.architers.model.query.DynamicColumnConditions;
import io.github.architers.model.query.WhereOperator;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//@Intercepts({@Signature(
//        type = StatementHandler.class,
//        method = "getBoundSql",
//        args = {}
//
//), @Signature(
//        type = StatementHandler.class,
//        method = "query",
//        args = {Statement.class, ResultHandler.class}
//
//)//,
////@Signature(type = StatementHandler.class,)
//})
//@Intercepts({@Signature(
//        type = Executor.class,
//        method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
//), @Signature(
//        type = Executor.class,
//        method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
//)})
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class,
                method = "getBoundSql", args = {})
})
@Component
public class MyIntest implements Interceptor {

    Map<String, Boolean> dymicMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 获取 StatementHandler 对象也就是执行语句
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 2. MetaObject 是 MyBatis 提供的一个反射帮助类，可以优雅访问对象的属性，这里是对 statementHandler 对象进行反射处理，
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
        // 3. 通过 metaObject 反射获取 statementHandler 对象的成员变量 mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // mappedStatement 对象的 id 方法返回执行的 mapper 方法的全路径名，如cn.zysheep.mapper.DynamicSqlMapper.count

        String id = mappedStatement.getId();
        // 4. 通过 id 获取到 Dao 层类的全限定名称，然后反射获取 Class 对象
        int index = id.lastIndexOf(".");
        Class<?> classType = Class.forName(id.substring(0, index));
        String methodName = id.substring(index + 1);
        // 5. 获取包含原始 sql 语句的 BoundSql 对象
        BoundSql boundSql = statementHandler.getBoundSql();

        // mappedStatement.getSqlSource(statementHandler.getBoundSql())
        String sql = boundSql.getSql();
        //  log.info("替换前---sql：{}", sql);
        // 拦截方法
        Object parameterObject = boundSql.getParameterObject();
        DynamicColumnConditions dynamicColumnConditions;
        if (parameterObject instanceof Map) {
            dynamicColumnConditions = (DynamicColumnConditions) ((Map<?, ?>) parameterObject).get("dynamicCondition");
        } else {
            dynamicColumnConditions = (DynamicColumnConditions) parameterObject;
        }
        BoundSql newBoundSql = null;
        String mSql = null;
        // 6. 遍历 Dao 层类的方法
        for (Method method : classType.getMethods()) {
            // 7. 判断方法上是否有 DynamicSql 注解，有的话，就认为需要进行 sql 替换
            if (method.getName().equals(methodName)) {

                if (method.isAnnotationPresent(DynamicSql.class)) {

                } else {
                    dymicMap.putIfAbsent(id, false);
                }
                Map<String, Object> map = new HashMap<>();

                mSql = sql.replaceAll("%columns%", getColumns());
                mSql = mSql.replaceAll("%wheres%", buildWhereSql(dynamicColumnConditions.getWheres(), map));
                // mSql = mSql.replaceAll("%orders%", buildOrders(dynamicColumnConditions.getOrders()));
                List<ParameterMapping> parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
                newBoundSql = new BoundSql(mappedStatement.getConfiguration(), mSql, parameterMappings, parameterObject);
                if (!CollectionUtils.isEmpty(map)) {
                    map.forEach((key, value) -> {
                        ParameterMapping.Builder builder = new ParameterMapping.Builder(mappedStatement.getConfiguration(), key, value.getClass());
                        if (value instanceof String) {
                            builder.jdbcType(JdbcType.VARCHAR);
                        }
                        builder.jdbcType(JdbcType.VARCHAR);
                        parameterMappings.add(builder.build());
                    });
                }
                if (!mSql.equals(boundSql.getSql())) {
                    metaObject.setValue("delegate.boundSql.sql", newBoundSql.getSql());
                    metaObject.setValue("delegate.boundSql.parameterMappings", newBoundSql.getParameterMappings());
                    metaObject.setValue("delegate.boundSql.additionalParameters", map);
                    metaObject.setValue("delegate.boundSql.metaParameters", mappedStatement.getConfiguration().newMetaObject(map));
                }

                break;
            }
        }
        // 9. 执行修改后的 SQL 语句。
        return invocation.proceed();

        //return invocation.proceed();
    }

    private String buildOrders(List<DynamicColumnConditions.OrderBy> orders) {

        if (CollectionUtils.isEmpty(orders)) {
            return "";
        }
        StringBuilder orderSql = new StringBuilder();
        orderSql.append(" order by");
        for (DynamicColumnConditions.OrderBy order : orders) {
            orderSql.append(" ");
            orderSql.append(order.getColumnName());
            orderSql.append(" ").append(order.isDesc() ? "desc" : "asc");
        }
        return orders.toString();
    }

    private String getColumns() {
        return "<foreach collection=\"columns\" item=\"column\" separator=\",\">\\${column.columnName} as \\${column.columnAlias} </foreach>";
    }

    private String buildWhereSql(List<DynamicColumnConditions.Where> whereList, Map<String, Object> map) {
        StringBuilder whereSql = new StringBuilder();
        String whereField = "d_w_";
        int index = 1;
        for (DynamicColumnConditions.Where where : whereList) {
            WhereOperator whereOperator = where.getOperator();
            whereSql.append(where.getColumnName());
            if (WhereOperator.equal.equals(whereOperator)) {
                whereSql.append(" = ");
                whereSql.append(" ? ");
                map.put(whereField + index, where.getValue());
            } else if (WhereOperator.like.equals(whereOperator)) {
                //  whereSql.append(" like '%?%'");
                whereSql.append(" like concat('%',?,'%') ");
                map.put(whereField + index, where.getValue());
            } else if (WhereOperator.likeLeft.equals(where.getOperator())) {
                whereSql.append(" like CONCAT('%',#{whereCondition.value})");
                // whereSql.append(" like '#{whereCondition.value}%'");
            } else if (WhereOperator.likeRight.equals(where.getOperator())) {
                whereSql.append(" like CONCAT(#{whereCondition.value},'%')");
                // whereSql.append(" like '#{whereCondition.value}%'");
            } else if (WhereOperator.between.equals(where.getOperator())) {
                String[] arr = where.getValue().toString().split(",");
                where.setConvertValue(arr);

                whereSql.append(" between #{whereCondition.convertValue[0]} and #{whereCondition.convertValue[1]}");
            } else if (WhereOperator.notBetween.equals(whereOperator)) {
                String[] arr = where.getValue().toString().split(",");
                where.setConvertValue(arr);
                whereSql.append(" not between #{whereCondition.convertValue[0]} and #{whereCondition.convertValue[1]}");
            } else if (WhereOperator.in.equals(whereOperator)) {
                String[] arr = where.getValue().toString().split(",");
                whereSql.append(" in (");
                for (int i = 0; i < arr.length; i++) {
                    map.put(whereField + index, arr[i]);
                    index++;
                    if (i == arr.length - 1) {
                        whereSql.append("?");
                    } else {
                        whereSql.append("?,");
                    }
                }
                whereSql.append(")");
            } else {
                throw new IllegalArgumentException("not support");
            }

            // where.setSql(whereSql.toString());
        }
        return whereSql.toString();
    }


    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
