package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.enums.CacheLevel;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ClassFieldsCache {

    private static final Map<Class<?>, Map<String/*originValueIndex*/, Map<CacheLevel, List<FieldConvertContext>>>> classGroupByOriginValueIndexMap = new ConcurrentHashMap<>();

    private static final Map<Class<?>, List<FieldConvertContext>> fieldConvertContextMap = new ConcurrentHashMap<>();

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    public static Map<String/*originValueIndex*/, Map<CacheLevel, List<FieldConvertContext>>> groupByOriginValueIndexAndCacheLevel(Class<?> clazz) {

        Map<String/*originValueIndex*/, Map<CacheLevel, List<FieldConvertContext>>> groupByOriginValueIndexMap = classGroupByOriginValueIndexMap.get(clazz);
        if (groupByOriginValueIndexMap == null) {
            groupByOriginValueIndexMap = new HashMap<>();
            Map<String, List<FieldConvertContext>> group = getFieldConvertContexts(clazz).stream().collect(Collectors.groupingBy(FieldConvertContext::getOriginValueIndex));
            Map<String, Map<CacheLevel, List<FieldConvertContext>>> finalGroupByOriginValueIndexMap = groupByOriginValueIndexMap;
            group.forEach((originValueIndex, contexts) -> finalGroupByOriginValueIndexMap.put(originValueIndex, contexts.stream().collect(Collectors.groupingBy(FieldConvertContext::getCacheLevel))));
            classGroupByOriginValueIndexMap.putIfAbsent(clazz, groupByOriginValueIndexMap);
        }
        return groupByOriginValueIndexMap;
    }

    /**
     * 得到原始值的converter为空的字段
     */
    public static List<FieldConvertContext> getFieldConvertContexts(Class<?> clazz) {

        List<FieldConvertContext> fieldConvertContexts = fieldConvertContextMap.get(clazz);
        if (fieldConvertContexts == null) {
            fieldConvertContexts = new ArrayList<>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                FieldConvertContext fieldConvertContext = buildFieldConvertContext(field, clazz);
                if (fieldConvertContext == null) {
                    continue;
                }
                fieldConvertContexts.add(fieldConvertContext);
            }
            fieldConvertContextMap.putIfAbsent(clazz, fieldConvertContexts);
        }

        return fieldConvertContexts;
    }


    private static FieldConvertContext buildFieldConvertContext(Field field, Class<?> clazz) {
        FieldConvert fieldConvert = field.getAnnotation(FieldConvert.class);
        if (fieldConvert == null) {
            return null;
        }
        FieldConvertContext fieldConvertContext = new FieldConvertContext();
        fieldConvertContext.setFieldConvert(fieldConvert);
        try {
            fieldConvertContext.setSourceField(clazz.getDeclaredField(fieldConvert.sourceField()));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        fieldConvertContext.setConvertField(field);
        String originDataConverter = StringUtils.isEmpty(fieldConvert.originValueConverter()) ? fieldConvert.converter() : fieldConvert.originValueConverter();
        String originValueIndex = String.join("_", originDataConverter, fieldConvertContext.getSourceField().getName());
        fieldConvertContext.setOriginValueIndex(originValueIndex);
        fieldConvertContext.setCacheLevel(fieldConvert.cacheLevel());
        return fieldConvertContext;
    }


}
