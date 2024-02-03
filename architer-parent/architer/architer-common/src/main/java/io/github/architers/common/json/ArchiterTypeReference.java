package io.github.architers.common.json;

import com.alibaba.fastjson2.util.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ArchiterTypeReference<T> {
    protected final Type type;
    protected final Class<? super T> rawType;

    /**
     * Constructs a new type literal. Derives represented class from type parameter.
     * <p>
     * Clients create an empty anonymous subclass. Doing so embeds the type
     * parameter in the anonymous class's type hierarchy, so we can reconstitute it at runtime despite erasure.
     */
    @SuppressWarnings("unchecked")
    public ArchiterTypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        rawType = (Class<? super T>) BeanUtils.getRawType(type);
    }

    public Type getType() {
        return type;
    }

    public Class<? super T> getRawType() {
        return rawType;
    }
}
