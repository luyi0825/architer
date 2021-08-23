package com.architecture.context.expression;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luyi
 * 缓存表带是解析
 */
public class ExpressionParser {


    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Object> valueCache = new ConcurrentHashMap<>();

    /**
     * 执行解析
     *
     * @param expressionMetadata key操作元数据
     * @param expression         表达式
     * @return SpEl表达式解析后的值
     */
    @Nullable
    public Object parserExpression(ExpressionMetadata expressionMetadata, String expression) {
        EvaluationContext evaluationContext = createEvaluationContext(expressionMetadata);
        Expression ex = getExpression(keyCache, expressionMetadata.getMethodKey(), expression);
        return ex.getValue(evaluationContext);
    }

    public List<Object> parserExpression(ExpressionMetadata expressionMetadata, String[] expressions) {
        List<Object> objects = new ArrayList<>(expressions.length);
        for (String expression : expressions) {
            org.springframework.expression.EvaluationContext evaluationContext = createEvaluationContext(expressionMetadata);
            Expression ex = getExpression(keyCache, expressionMetadata.getMethodKey(), expression);
            objects.add(Objects.requireNonNull(ex.getValue(evaluationContext)));
        }
        return objects;
    }

    /**
     * 解析固定的表达式:表达式的值不会变
     */
    public List<Object> parserFixExpression(ExpressionMetadata expressionMetadata, String[] expressions) {
        List<Object> objects = new ArrayList<>(expressions.length);
        for (String expression : expressions) {
            ExpressionKey expressionKey = createKey(expressionMetadata.getMethodKey(), expression);
            Object value = valueCache.get(expressionKey);
            if (value == null) {
                org.springframework.expression.EvaluationContext evaluationContext = createEvaluationContext(expressionMetadata);
                Expression ex = getExpression(keyCache, expressionKey);
                value = Objects.requireNonNull(ex.getValue(evaluationContext));
                valueCache.putIfAbsent(expressionKey, value);
            }
            objects.add(value);
        }
        return objects;
    }


    private EvaluationContext createEvaluationContext(ExpressionMetadata metadata) {
        ExpressionRootObject rootObject = new ExpressionRootObject(metadata.getTargetMethod(), metadata.getArgs(), metadata.getTarget(), metadata.getTargetClass());
        return new EvaluationContext(
                rootObject, metadata.getTargetMethod(), metadata.getArgs(), new DefaultParameterNameDiscoverer());
    }


    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       ExpressionKey expressionKey) {
        Expression expr = cache.get(expressionKey);
        if (expr == null) {
            expr = parser.parseExpression(expressionKey.expression);
            cache.put(expressionKey, expr);
        }
        return expr;
    }

    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       AnnotatedElementKey elementKey, String expression) {
        ExpressionKey expressionKey = createKey(elementKey, expression);
        return getExpression(cache, expressionKey);
    }

    private ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
        return new ExpressionKey(elementKey, expression);
    }


    static class ExpressionKey implements Comparable<ExpressionKey> {

        private final AnnotatedElementKey element;

        private final String expression;

        protected ExpressionKey(AnnotatedElementKey element, String expression) {
            Assert.notNull(element, "AnnotatedElementKey must not be null");
            Assert.notNull(expression, "Expression must not be null");
            this.element = element;
            this.expression = expression;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpressionKey)) {
                return false;
            }
            ExpressionKey otherKey = (ExpressionKey) other;
            return (this.element.equals(otherKey.element) &&
                    ObjectUtils.nullSafeEquals(this.expression, otherKey.expression));
        }

        @Override
        public int hashCode() {
            return this.element.hashCode() * 29 + this.expression.hashCode();
        }

        @Override
        public String toString() {
            return this.element + " with expression \"" + this.expression + "\"";
        }

        @Override
        public int compareTo(ExpressionKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0) {
                result = this.expression.compareTo(other.expression);
            }
            return result;
        }
    }
}