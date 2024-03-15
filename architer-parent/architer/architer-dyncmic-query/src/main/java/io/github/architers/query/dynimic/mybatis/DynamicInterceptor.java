package io.github.architers.query.dynimic.mybatis;

import io.github.architers.common.json.JsonUtils;
import io.github.architers.query.dynimic.DynamicColumnConditions;
import io.github.architers.query.dynimic.Where;
import io.github.architers.query.dynimic.WhereCondition;
import io.github.architers.query.dynimic.WhereOperator;
import io.github.architers.query.dynimic.annotation.DynamicQuery;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 动态sql拦截器
 *
 * @author luyi
 * @since 1.0.3
 */
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class,
                method = "getBoundSql", args = {})
})
public class DynamicInterceptor implements Interceptor {

    private static final String WHERE_FIELD_PREFIX = "d_w_";

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
        BoundSql newBoundSql;
        String mSql = null;
        // 6. 遍历 Dao 层类的方法
        for (Method method : classType.getMethods()) {
            // 7. 判断方法上是否有 DynamicSql 注解，有的话，就认为需要进行 sql 替换
            if (method.getName().equals(methodName)) {

                if (method.isAnnotationPresent(DynamicQuery.class)) {

                } else {
                    dymicMap.putIfAbsent(id, false);
                }
                Map<String, Object> map = new HashMap<>();
                //找出后边还有多少?
                // String endSql = sql.substring(mSql.indexOf("%wheres%") + 1);
                // endSql.indexOf("?");

                mSql = sql.replaceAll("%columns%", getColumns(dynamicColumnConditions.getColumns()));

                /*算下参数*/


                mSql = mSql.replaceAll("%wheres%", buildWhereSql(dynamicColumnConditions.getWheres(), map));
                // mSql = mSql.replaceAll("%orders%", buildOrders(dynamicColumnConditions.getOrders()));
                List<ParameterMapping> parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
                newBoundSql = new BoundSql(mappedStatement.getConfiguration(), mSql, parameterMappings, parameterObject);
                if (!CollectionUtils.isEmpty(map)) {
                    map.forEach((key, value) -> {
                        ParameterMapping.Builder builder = new ParameterMapping.Builder(mappedStatement.getConfiguration(), key, value.getClass());
//                        if (value instanceof String) {
//                            builder.jdbcType(JdbcType.VARCHAR);
//                        }
//                        builder.jdbcType(JdbcType.VARCHAR);
                        parameterMappings.add(builder.build());
                    });
                }
                if (!mSql.equals(boundSql.getSql())) {
                    metaObject.setValue("delegate.boundSql.sql", newBoundSql.getSql());
                    metaObject.setValue("delegate.boundSql.parameterMappings", newBoundSql.getParameterMappings());
                    System.out.println(JsonUtils.toJsonString(newBoundSql.getParameterMappings()));
                    newBoundSql.getAdditionalParameters().putAll(map);
                    metaObject.setValue("delegate.boundSql.additionalParameters", map);
                    metaObject.setValue("delegate.boundSql.metaParameters", mappedStatement.getConfiguration().newMetaObject(newBoundSql.getAdditionalParameters()));
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
        return orderSql.toString();
    }

    private String getColumns(List<DynamicColumnConditions.Column> columns) {
        if (CollectionUtils.isEmpty(columns)) {
            return "";
        }
        StringBuilder columnSql = new StringBuilder();
        int index = 0;
        for (DynamicColumnConditions.Column column : columns) {
            columnSql.append(column.getColumnName()).append(" as '")
                    .append(column.getColumnAlias())
                    .append("'");
            if (index != columns.size() - 1) {
                columnSql.append(",");
            }
            index++;
        }
        return columnSql.toString();
    }



    private String buildWhereSql(List<Where> whereList, Map<String, Object> map) {
        if (!CollectionUtils.isEmpty(whereList)) {
            StringBuilder whereSql = new StringBuilder();
            int index = 1;
            appendWhere(whereSql, whereList, map, index);
            return whereSql.toString();
        }
        return "";

    }

    private void appendWhere(StringBuilder whereSql, List<Where> wheres, Map<String, Object> map, int index) {
        for (Where where : wheres) {
            if (where.isAnd()) {
                whereSql.append(" and ( ");
            } else {
                whereSql.append(" or ( ");
            }
            if (!CollectionUtils.isEmpty(where.getWhereConditions())) {
                index = this.appendWhereConditions(whereSql, where.getWhereConditions(), map, index);
            }
            if (!CollectionUtils.isEmpty(where.getWheres())) {
                appendWhere(whereSql, where.getWheres(), map, index);
            }
            whereSql.append(" )");

        }
    }

    private int appendWhereConditions(StringBuilder whereSql, List<WhereCondition> whereConditions, Map<String, Object> map, int index) {
        for (WhereCondition whereCondition : whereConditions) {
            WhereOperator whereOperator = whereCondition.getOperator();
            whereSql.append(whereCondition.getColumnName());
            if (WhereOperator.equal.equals(whereOperator)) {
                whereSql.append(" = ");
                whereSql.append(" ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else if (WhereOperator.notEqual.equals(whereOperator)) {
                whereSql.append(" != ");
                whereSql.append(" ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else if (WhereOperator.like.equals(whereOperator)) {
                //  whereSql.append(" like '%?%'");
                whereSql.append(" like ? ");
                map.put(WHERE_FIELD_PREFIX + index, "%" + whereCondition.getValue().toString() + "%");
                index++;
            } else if (WhereOperator.likeLeft.equals(whereCondition.getOperator())) {
                whereSql.append(" like ? ");
                map.put(WHERE_FIELD_PREFIX + index, "%" + whereCondition.getValue().toString());
                index++;
            } else if (WhereOperator.likeRight.equals(whereCondition.getOperator())) {
                whereSql.append(" like ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue().toString() + "%");
                index++;
            } else if (WhereOperator.between.equals(whereCondition.getOperator())) {
                String[] arr = whereCondition.getValue().toString().split(",");
                whereSql.append(" between ? and ? ");
                map.put(WHERE_FIELD_PREFIX + index, arr[0]);
                index++;
                map.put(WHERE_FIELD_PREFIX + index, arr[1]);
                index++;
            } else if (WhereOperator.notBetween.equals(whereOperator)) {
                String[] arr = whereCondition.getValue().toString().split(",");
                whereSql.append(" not between ? and ? ");
                map.put(WHERE_FIELD_PREFIX + index, arr[0]);
                index++;
                map.put(WHERE_FIELD_PREFIX + index, arr[1]);
                index++;
            } else if (WhereOperator.in.equals(whereOperator)) {
                whereSql.append(" in ");
                index = index + this.buildInData(whereCondition.getValue(), map, index, whereSql);
            } else if (WhereOperator.notIn.equals(whereOperator)) {
                whereSql.append(" not in ");
                index = index + this.buildInData(whereCondition.getValue(), map, index, whereSql);
            } else if (WhereOperator.ge.equals(whereOperator)) {
                whereSql.append(" >= ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else if (WhereOperator.gt.equals(whereOperator)) {
                whereSql.append(" > ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else if (WhereOperator.lt.equals(whereOperator)) {
                whereSql.append(" < ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else if (WhereOperator.le.equals(whereOperator)) {
                whereSql.append(" <= ? ");
                map.put(WHERE_FIELD_PREFIX + index, whereCondition.getValue());
                index++;
            } else {
                throw new IllegalArgumentException("not support");
            }
        }
        return index;
    }

    private int buildInData(Object value,
                            Map<String, Object> map,
                            int index,
                            StringBuilder whereSql) {
        String[] arr = value.toString().split(",");
        whereSql.append("(");
        for (int i = 0; i < arr.length; i++) {
            map.put(WHERE_FIELD_PREFIX + index, arr[i]);
            index++;
            if (i == arr.length - 1) {
                whereSql.append("?");
            } else {
                whereSql.append("?,");
            }
        }
        whereSql.append(")");
        return arr.length;

    }


    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }
}
