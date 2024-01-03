package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.CacheNameManager;
import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.fieldconvert.anantion.NeedFieldConvert;
import io.github.architers.context.cache.model.BatchGetParam;
import io.github.architers.context.cache.model.GetParam;
import io.github.architers.context.cache.model.InvalidCacheValue;
import io.github.architers.context.cache.operate.CacheOperateManager;
import io.github.architers.context.cache.operate.LocalAndRemoteCacheOperate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * TODO 一个数据为空一直查询数据库没解决
 * 数据转码转发器
 *
 * @author luyi
 */
@Component
@Slf4j
public class FieldConvertSupport implements ApplicationContextAware {

    /**
     * 最大的深度
     */
    private static final int MAX_DEPTH = 5;

    @Resource
    private CacheOperateManager cacheOperateManager;

    @Resource
    private CacheFieldConvertProperties cacheFieldConvertProperties;

    @Resource
    private CacheNameManager cacheNameManager;


    private final Map<String/*转换器名称*/, IFieldValueConvert<Object>> fieldValueConvertMap = new HashMap<>();

    private final Map<String, IFieldOriginDataObtain<?>> fieldOriginDataObtainMap = new HashMap<>();


    public void convertData(Object data, TempCache tempCache, boolean cacheQueryValue) {
        long startTime = System.currentTimeMillis();
        int depth = 1;
        AtomicLong cost = new AtomicLong();
        convertData(data, depth, cost, tempCache, false, cacheQueryValue);
        long time = System.currentTimeMillis() - startTime;
        if (time > 2000) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }
    }

    /**
     * @param tempCacheParsed 临时缓存已经解析
     * @param cacheQueryValue 缓存查询数据库的值
     */
    private void convertData(Object data,
                             int depth,
                             AtomicLong cost,
                             TempCache tempCache,
                             boolean tempCacheParsed,
                             boolean cacheQueryValue) {
        if (data == null) {
            return;
        }
        if (++depth > MAX_DEPTH) {
            throw new IllegalStateException("超过最大深度:" + MAX_DEPTH);
        }
        if (data instanceof Collection) {
            convertCollectionData(data, depth, cost, tempCache, cacheQueryValue);
        } else {
            //不是集合的就进行字段转换
            for (Field declaredField : data.getClass().getDeclaredFields()) {
                if (declaredField.getAnnotation(NeedFieldConvert.class) != null) {
                    try {
                        declaredField.setAccessible(true);
                        Object fieldData = declaredField.get(data);
                        if (fieldData != null) {
                            convertData(fieldData, depth, cost, tempCache, false, cacheQueryValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (tempCache == null) {
                //转换单个数据
                convertSingleData(data);
            } else {
                if (!tempCacheParsed) {
                    parseTempCacheManyCacheValue(data, tempCache);
                }
                //通过临时缓存转换单个数据
                convertSingeDataWithTempCache(data, tempCache);
            }

        }
    }

    private void convertCollectionData(Object data,
                                       int depth,
                                       AtomicLong cost,
                                       TempCache tempCache, boolean cacheQueryValue) {
        if (CollectionUtils.isEmpty((Collection<?>) data)) {
            //数据为空
            return;
        }
        ++depth;
        parseTempCacheManyCacheValue(data, tempCache);
        int finalDepth = depth;
        ((Collection<?>) data).forEach(e -> convertData(e, finalDepth, cost, tempCache, true, cacheQueryValue));

    }

    private void parseTempCacheManyCacheValue(Object data, TempCache tempCache) {
        if (tempCache == null) {
            return;
        }
        long startTime = System.currentTimeMillis();
        Map<String/*converter*/, Map<CacheLevel, Set<String>>> keyValueMap = new HashMap<>();
        if (data instanceof Collection) {
            ((Collection<?>) data).forEach(e -> parseTempCacheKeyInfo(e, keyValueMap));
        } else {
            parseTempCacheKeyInfo(data, keyValueMap);
        }
        log.info("解析临时缓存key耗时:" + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        keyValueMap.forEach((converter, cacheLevelMap) -> cacheLevelMap.forEach((cacheLevel, keys) -> {
            Map<String, Serializable> valueMap;
            Collection<String> notMatchKeys = null;
            LocalAndRemoteCacheOperate cacheOperate = this.getCacheOperate(converter);
            if (CacheLevel.none.equals(cacheLevel)) {
                //直接从DB
                valueMap = this.getFieldOriginDataObtain(converter).getConvertOriginDatas(keys);
            } else if (CacheLevel.remote.equals(cacheLevel)) {
                //TODOconverter, keys
                BatchGetParam batchGetParam = new BatchGetParam();
                batchGetParam.setKeys(keys);
                Map<String, Serializable> remoteValueMap = cacheOperate.getRemoteCacheOperate().batchGet(batchGetParam);
                notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(keys, remoteValueMap.keySet());
                valueMap = remoteValueMap;
            } else if (CacheLevel.local.equals(cacheLevel)) {
                BatchGetParam batchGetParam = new BatchGetParam();
                batchGetParam.setKeys(keys);
                Map<String, Serializable> localValueMap = cacheOperate.getRemoteCacheOperate().batchGet(batchGetParam);
                notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(keys, localValueMap.keySet());
                valueMap = localValueMap;
            } else {
                throw new RuntimeException("cacheLevel错误");
            }
            if (notMatchKeys != null && !CollectionUtils.isEmpty(notMatchKeys)) {
                //本地缓存或者remote缓存都没，从数据库中查询
                Map<String, Serializable> dbMap = this.getFieldOriginDataObtain(converter).getConvertOriginDatas(notMatchKeys);
                if (!CollectionUtils.isEmpty(dbMap)) {
                    //将db中查询的数据，放到缓存中
                    valueMap.putAll(dbMap);
                }
                //异步放到缓存
                asyncPutCache(converter, dbMap, cacheLevel);
            }
            valueMap.forEach((key, value) -> {
                String tempCacheKey = getTempCacheKey(cacheLevel, converter, key);
                tempCache.put(tempCacheKey, value);
            });

        }));
        log.info("准备临时缓存数据耗时:" + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> converters = new HashSet<>();
        applicationContext.getBeansOfType(IFieldConvert.class).forEach((key, value) -> {
            if (value instanceof IFieldValueConvert) {
                if (fieldValueConvertMap.putIfAbsent(value.getConverter(), (IFieldValueConvert<Object>) value) != null) {
                    throw new RuntimeException(value.getConverter() + "重复");
                }
            }
            if (value instanceof IFieldValueConvert) {
                fieldOriginDataObtainMap.put(value.getConverter(), (IFieldOriginDataObtain<?>) value);
            }
            //converter不能重复
            if (converters.contains(value.getConverter())) {
                throw new RuntimeException(value.getConverter() + "重复");
            }
            converters.add(value.getConverter());
        });
    }

    public IFieldOriginDataObtain getFieldOriginDataObtain(String converter) {
        return fieldOriginDataObtainMap.get(converter);
    }

    /**
     * 转换(数据会临时缓存）
     */
    public void convertSingeDataWithTempCache(Object data, TempCache tempCache) {
        for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
            FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
            String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
            if (!StringUtils.hasText(sourceFieldStrValue)) {
                //值为空
                setConvertFieldValue(null, null, data, fieldConvertContext.getConvertField());
                continue;
            }

            Object cacheOriginValue = null;
            String simpleCacheKey = getTempCacheKey(fieldConvert.cacheLevel(), fieldConvertContext.getOriginValueConverter(), sourceFieldStrValue);

            //先从本地缓存获取
            if (CacheLevel.isContainLocal(fieldConvert.cacheLevel())) {
                LocalAndRemoteCacheOperate localAndRemoteCacheOperate = getCacheOperate(fieldConvertContext.getOriginValueConverter());
                GetParam getParam = new GetParam();
                getParam.setKey(sourceFieldStrValue);
                getParam.setWrapperCacheName(null);
                cacheOriginValue = localAndRemoteCacheOperate.getLocalCacheOperate().get(getParam);
                tempCache.get(simpleCacheKey);
            }
            if (cacheOriginValue == null && tempCache != null && tempCache.isCanExpire()) {

            }


            if (cacheOriginValue == null && tempCache != null) {
                //临时缓存查询不到，再从全局缓存和数据库查询(只有能够过期的才能查询，不能过期的当时就查询出来了)
                cacheOriginValue = this.getAndCacheOriginData(fieldConvertContext, sourceFieldStrValue);
                tempCache.put(simpleCacheKey, cacheOriginValue == null ? InvalidCacheValue.INVALID_CACHE : cacheOriginValue);
            }
            //设置转换值
            this.setConvertFieldValue(cacheOriginValue, fieldConvert.converter(), data, fieldConvertContext.getConvertField());
        }

    }

    private void parseTempCacheKeyInfo(Object data, Map<String, Map<CacheLevel, Set<String>>> keyValueMap) {
        ClassFieldsCache.groupByOriginValueIndexAndCacheLevel(data.getClass()).forEach((index, cacheLevelListMap) -> {
            cacheLevelListMap.forEach((cacheLevel, fieldContexts) -> {
                for (FieldConvertContext fieldContext : fieldContexts) {
                    Map<CacheLevel, Set<String>> cacheLevelKeysMap = keyValueMap.computeIfAbsent(fieldContext.getOriginValueConverter(), k -> new HashMap<>());
                    String value = fieldContext.getSourceFieldStrValue(data);
                    CacheLevel cacheLevelKey = getTempCacheLevelKey(cacheLevel);
                    Set<String> converterKeys = cacheLevelKeysMap.computeIfAbsent(cacheLevelKey, k -> new HashSet<>());
                    if (value != null) {
                        converterKeys.add(value);
                    }
                }
            });
        });
    }


    public void convertSingleData(Object data) {
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
            if (originData != null && !(originData instanceof InvalidCacheValue)) {
                IFieldValueConvert<Object> originValueConvert = fieldValueConvertMap.get(converter);
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
        LocalAndRemoteCacheOperate localAndRemoteCacheOperate = this.getCacheOperate(originValueConverter);
        GetParam getParam = new GetParam();
        getParam.setKey(sourceFieldStrValue);
        getParam.setAsync(false);
        getParam.setWrapperCacheName(getWrapperCacheName(originValueConverter));
        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
            originData = localAndRemoteCacheOperate.get(getParam);
        } else if (CacheLevel.local.equals(cacheLevel)) {
            originData = localAndRemoteCacheOperate.getLocalCacheOperate().get(getParam);
        } else if (CacheLevel.remote.equals(cacheLevel)) {
            originData = localAndRemoteCacheOperate.getRemoteCacheOperate().get(getParam);
        }
        if (originData != null) {
            return originData;
        }
        IFieldOriginDataObtain<?> originValueObtain = fieldOriginDataObtainMap.get(originValueConverter);
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
//        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutAllLocal(converter, cacheData);
//            originValueObtainSupport.asyncPutAllRemote(converter, cacheData);
//        } else if (CacheLevel.local.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutAllLocal(converter, cacheData);
//        } else if (CacheLevel.remote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutAllRemote(converter, cacheData);
//        }
    }

    private void asyncPutCache(String converter, String fieldStrValue, CacheLevel cacheLevel, Object value) {
//        if (value == null) {
//            return;
//        }
//        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutLocalAndRemote(converter, fieldStrValue, value);
//        } else if (CacheLevel.local.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutLocal(converter, fieldStrValue, value);
//        } else if (CacheLevel.remote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutRemote(converter, fieldStrValue, value);
//        }
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

    private LocalAndRemoteCacheOperate getCacheOperate(String converter) {
        String cacheName = cacheFieldConvertProperties.getCacheNames().get(converter);
        LocalAndRemoteCacheOperate localAndRemoteCacheOperate = cacheOperateManager.getCacheOperate(cacheName);
        Assert.notNull(localAndRemoteCacheOperate, "请先配置字段转换器对应的缓存匹配信息:" + converter);
        return localAndRemoteCacheOperate;
    }

    private String getWrapperCacheName(String converter) {
        String cacheName = cacheFieldConvertProperties.getCacheNames().get(converter);
        if (!StringUtils.hasText(cacheName)) {
            throw new IllegalArgumentException("converter没有配置缓存名称:" + converter);
        }
        return cacheNameManager.getWrapperCacheName(cacheName);
    }


}
