package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;


/**
 * TODO 一个数据为空一直查询数据库没解决
 * 数据转码转发器
 *
 * @author luyi
 */
@Service
public class DataConvertDispatcher implements ApplicationContextAware {

    @Getter
    @Resource
    private OriginValueObtainSupport originValueObtainSupport;


    private final Map<String/*转换器名称*/, IFieldValueConvert<Object>> originValueConvertMap = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> converters = new HashSet<>();
        applicationContext.getBeansOfType(IFieldConvert.class).forEach((key, value) -> {
            if (value instanceof IFieldValueConvert) {
                if (originValueConvertMap.putIfAbsent(value.getConverter(), (IFieldValueConvert<Object>) value) != null) {
                    throw new RuntimeException(value.getConverter() + "重复");
                }
            }
            //converter不能重复
            if (converters.contains(value.getConverter())) {
                throw new RuntimeException(value.getConverter() + "重复");
            }
            converters.add(value.getConverter());

        });
    }

    /**
     * 转换(数据会临时缓存）
     */
    public void convertManyDataWithCache(Object data, TempCache tempCache) {
        for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
            FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
            String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
            if (StringUtils.isEmpty(sourceFieldStrValue)) {
                //值为空
                setConvertFieldValue(null, null, data, fieldConvertContext.getConvertField());
                continue;
            }
            String simpleCacheKey = getTempCacheKey(fieldConvert.cacheLevel(), fieldConvertContext.getOriginValueConverter(), sourceFieldStrValue);
            Object cacheOriginValue = tempCache.get(simpleCacheKey);
            if (cacheOriginValue == null && tempCache.isCanExpire()) {
                //临时缓存查询不到，再从全局缓存和数据库查询(只有能够过期的才能查询，不能过期的当时就查询出来了)
                cacheOriginValue = this.getAndCacheOriginData(fieldConvertContext, sourceFieldStrValue);
                tempCache.put(simpleCacheKey, cacheOriginValue == null ? NullValue.value : cacheOriginValue);
            }
            //设置转换值
            this.setConvertFieldValue(cacheOriginValue, fieldConvert.converter(), data, fieldConvertContext.getConvertField());
        }

    }

    public void convertData(Object data) {
        try {
            for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
                //临时缓存查询不到，再从全局缓存和数据库查询
                String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
                Object originData = this.getAndCacheOriginData(fieldConvertContext, sourceFieldStrValue);
                //设置转换值
                this.setConvertFieldValue(originData, fieldConvertContext.getFieldConvert().converter(), data, fieldConvertContext.getConvertField());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void setConvertFieldValue(Object originData, String converter, Object data, Field convertField) {
        try {
            if (originData != null && !(originData instanceof NullValue)) {
                IFieldValueConvert<Object> originValueConvert = originValueConvertMap.get(converter);
                if (originValueConvert == null) {
                    throw new RuntimeException("数据转换器为空:" + converter);
                }
                Object convertValue = originValueConvert.getConvertValue(originData);
                convertField.setAccessible(true);
                convertField.set(data, convertValue);
                return;
            }
            convertField.setAccessible(true);
            convertField.set(data, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String getSourceFieldStrValue(FieldConvertContext fieldConvertContext, Object data) {

        //SourceField为空，那么转换的字段也为空
        Object sourceFieldValue;
        try {
            fieldConvertContext.getSourceField().setAccessible(true);
            sourceFieldValue = fieldConvertContext.getSourceField().get(data);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(sourceFieldValue)) {
            //为空返回
            return null;
        }
        return sourceFieldValue.toString();
    }

    private Object getAndCacheOriginData(FieldConvertContext fieldConvertContext, String sourceFieldStrValue) {
        if (sourceFieldStrValue == null) {
            return null;
        }

        FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
        CacheLevel cacheLevel = fieldConvert.cacheLevel();
        String originValueConverter = fieldConvertContext.getOriginValueConverter();
        String cacheName = "dataConverter:" + originValueConverter;
        Object originData = null;
        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
            originData = originValueObtainSupport.getFromLocalOrRemote(cacheName, sourceFieldStrValue);
        } else if (CacheLevel.local.equals(cacheLevel)) {
            originData = originValueObtainSupport.getFromLocal(cacheName, sourceFieldStrValue);
        } else if (CacheLevel.remote.equals(cacheLevel)) {
            originData = originValueObtainSupport.getFromRemote(cacheName, sourceFieldStrValue);
        }
        if (originData != null) {
            return originData;
        }
        IFieldOriginDataObtain<?> originValueObtain = originValueObtainSupport.getConvertOriginValueObtain(originValueConverter);
        if (originValueObtain == null) {
            throw new RuntimeException("原始数据获取的转换器不能为空:" + originValueConverter);
        }
        try {
            originData = originValueObtain.getConvertOriginData(sourceFieldStrValue);
            return originData;
        } finally {
            if (originData != null) {
                asyncPutCache(cacheName, sourceFieldStrValue, cacheLevel, originData);
            }
        }

    }

    public void asyncPutCache(String converter, Map<String, Serializable> cacheData, CacheLevel cacheLevel) {
        if (CollectionUtils.isEmpty(cacheData)) {
            return;
        }
        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutAllLocal(converter, cacheData);
            originValueObtainSupport.asyncPutAllRemote(converter, cacheData);
        } else if (CacheLevel.local.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutAllLocal(converter, cacheData);
        } else if (CacheLevel.remote.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutAllRemote(converter, cacheData);
        }
    }

    private void asyncPutCache(String converter, String fieldStrValue, CacheLevel cacheLevel, Object value) {
        if (value == null) {
            return;
        }
        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutLocalAndRemote(converter, fieldStrValue, value);
        } else if (CacheLevel.local.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutLocal(converter, fieldStrValue, value);
        } else if (CacheLevel.remote.equals(cacheLevel)) {
            originValueObtainSupport.asyncPutRemote(converter, fieldStrValue, value);
        }
    }

    public CacheLevel getTempCacheLevelKey(CacheLevel cacheLevel) {
        if (CacheLevel.none.equals(cacheLevel)) {
            return CacheLevel.none;
        } else if (CacheLevel.local.equals(cacheLevel)) {
            return CacheLevel.local;
        } else {
            return CacheLevel.remote;
        }
    }

    public String getTempCacheKey(CacheLevel cacheLevel, String converter, String dataKey) {
        if (CacheLevel.none.equals(cacheLevel)) {
            return String.join("_", converter, dataKey, cacheLevel.name());
        }
        return String.join("_", converter, dataKey);
    }


    /**
     * 标识查询了,返回的为空
     */
    static final class NullValue implements Serializable {

        public static final NullValue value = new NullValue();

    }


}
