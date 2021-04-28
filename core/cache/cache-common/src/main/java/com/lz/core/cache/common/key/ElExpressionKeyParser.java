package com.lz.core.cache.common.key;

import com.lz.core.cache.common.operation.CacheOperationMetadata;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luyi
 * el表达式key解析器
 */
public class ElExpressionKeyParser {


    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);

    @Nullable
    public String generateKey(CacheOperationMetadata cacheOperationMetadata, String expression) {
        EvaluationContext evaluationContext = createEvaluationContext(cacheOperationMetadata);
        Expression ex = getExpression(keyCache, cacheOperationMetadata.getMethodKey(), expression);
        return Objects.requireNonNull(ex.getValue(evaluationContext)).toString();
    }


    private EvaluationContext createEvaluationContext(CacheOperationMetadata metadata) {
        CacheExpressionRootObject rootObject = new CacheExpressionRootObject(metadata.getTargetMethod(), metadata.getArgs(), metadata.getTarget(), metadata.getTargetClass());
        return new CacheEvaluationContext(
                rootObject, metadata.getTargetMethod(), metadata.getArgs(), new DefaultParameterNameDiscoverer());
    }

    protected Expression getExpression(Map<ExpressionKey, Expression> cache,
                                       AnnotatedElementKey elementKey, String expression) {

        ExpressionKey expressionKey = createKey(elementKey, expression);
        Expression expr = cache.get(expressionKey);
        if (expr == null) {
            expr = parser.parseExpression(expression);
            cache.put(expressionKey, expr);
        }
        return expr;
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
