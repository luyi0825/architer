package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.CacheNameManager;
import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.fieldconvert.anantion.NeedFieldConvert;
import io.github.architers.context.cache.model.BatchGetParam;
import io.github.architers.context.cache.model.GetParam;
import io.github.architers.context.cache.model.InvalidCacheValue;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.operate.CacheOperateManager;
import io.github.architers.context.cache.operate.LocalAndRemoteCacheOperate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
        if (tempCache == null && data instanceof Collection) {
            if (((Collection<?>) data).size() > 1) {
                //集合默认使用HashMap的临时缓存
                tempCache = new HashMapTempCache(((Collection<?>) data).size());
            }
        }
        convertData(data, depth, tempCache, false, cacheQueryValue);
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
            convertCollectionData(data, depth, tempCache, cacheQueryValue);
        } else {
            //不是集合的就进行字段转换
            for (Field declaredField : data.getClass().getDeclaredFields()) {
                if (declaredField.getAnnotation(NeedFieldConvert.class) != null) {
                    try {
                        declaredField.setAccessible(true);
                        Object fieldData = declaredField.get(data);
                        if (fieldData != null) {
                            convertData(fieldData, depth, tempCache, false, cacheQueryValue);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (tempCache == null) {
                //转换单个数据
                convertSingleData(data, cacheQueryValue);
            } else {
                if (!tempCacheParsed) {
                    parseTempCacheManyCacheValue(data, tempCache);
                }
                //通过临时缓存转换单个数据
                convertSingeDataWithTempCache(data, tempCache, cacheQueryValue);
            }

        }
    }

    private void convertCollectionData(Object data,
                                       int depth,
                                       TempCache tempCache, boolean cacheQueryValue) {
        if (CollectionUtils.isEmpty((Collection<?>) data)) {
            //数据为空
            return;
        }
        ++depth;
        parseTempCacheManyCacheValue(data, tempCache);
        int finalDepth = depth;
        ((Collection<?>) data).forEach(e -> convertData(e, finalDepth, tempCache, true, cacheQueryValue));

    }


    private void parseTempCacheManyCacheValue(Object data, TempCache tempCache) {
        if (tempCache == null) {
            return;
        }
        long startTime = System.currentTimeMillis();
        Map<String/*converter*/, Map<CacheLevel, Set<String>>> converterCacheLevelKeyMap = this.parseConverterCacheLevelKeys(data);

        log.info("解析临时缓存key耗时:" + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        converterCacheLevelKeyMap.forEach((converter, cacheLevelMap) -> cacheLevelMap.forEach((cacheLevel, keys) -> {
            Map<String, Serializable> valueMap = null;
            Collection<String> notMatchKeys = null;
            LocalAndRemoteCacheOperate cacheOperate = this.getCacheOperate(converter);
            //从db和remote获取之前，先判断本地有没有
            Set<String> notInTempCacheKeys = this.notInTempCacheKeys(keys, cacheLevel, converter, tempCache);
            if (!CacheLevel.local.equals(cacheLevel)) {
                if (!CollectionUtils.isEmpty(notInTempCacheKeys)) {
                    if (CacheLevel.none.equals(cacheLevel)) {
                        //直接从DB
                        valueMap = getFieldOriginDataObtain(converter).getConvertOriginDatas(notInTempCacheKeys);
                    } else {
                        //从remote
                        BatchGetParam batchGetParam = new BatchGetParam();
                        batchGetParam.setKeys(notInTempCacheKeys);
                        Map<String, Serializable> remoteValueMap = cacheOperate.getRemoteCacheOperate().batchGet(batchGetParam);
                        //取差集
                        notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(notInTempCacheKeys, remoteValueMap.keySet());
                        valueMap = remoteValueMap;
                    }
                }
            }
            if (valueMap != null) {
                valueMap.forEach((key, value) -> {
                    String tempCacheKey = getTempCacheKey(cacheLevel, converter, key);
                    tempCache.put(tempCacheKey, value);
                });
            }
            if (notMatchKeys != null && !CollectionUtils.isEmpty(notMatchKeys)) {
                //本地缓存或者remote缓存都没，从数据库中查询
                Map<String, Serializable> dbMap = this.getFieldOriginDataObtain(converter).getConvertOriginDatas(notMatchKeys);
                if (!CollectionUtils.isEmpty(dbMap)) {
                    dbMap.forEach((key, value) -> {
                        String tempCacheKey = getTempCacheKey(cacheLevel, converter, key);
                        tempCache.put(tempCacheKey, value);
                    });
                }
                //异步放到缓存
                asyncPutCache(converter, dbMap, cacheLevel);
            }
        }));
        log.info("准备临时缓存数据耗时:" + (System.currentTimeMillis() - startTime));
    }

    private Set<String> notInTempCacheKeys(Set<String> keys, CacheLevel cacheLevel, String originDataConverter, TempCache tempCache) {
        Set<String> notInTempCacheKeys = new HashSet<>(keys.size(), 1);
        for (String key : keys) {
            String tempCacheKey = getTempCacheKey(cacheLevel, originDataConverter, key);
            if (!tempCache.containsKey(tempCacheKey)) {
                notInTempCacheKeys.add(key);
            }
        }
        return notInTempCacheKeys;
    }

    private Map<String, Map<CacheLevel, Set<String>>> parseConverterCacheLevelKeys(Object data) {
        Map<String/*converter*/, Map<CacheLevel, Set<String>>> converterCacheLevelKeyMap = new HashMap<>();
        {
            //构建数据的缓存key，批量查询数据
            if (data instanceof Collection) {
                ((Collection<?>) data).forEach(e -> parseTempCacheKeyInfo(e, converterCacheLevelKeyMap));
            } else {
                parseTempCacheKeyInfo(data, converterCacheLevelKeyMap);
            }
        }
        return converterCacheLevelKeyMap;
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
    public void convertSingeDataWithTempCache(Object data, TempCache tempCache, Boolean cacheQueryValue) {
        for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
            FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
            String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
            if (!StringUtils.hasText(sourceFieldStrValue)) {
                //值为空
                setConvertFieldValue(null, null, data, fieldConvertContext.getConvertField());
                continue;
            }
            Object originValue;
            String tempCacheKey = getTempCacheKey(fieldConvert.cacheLevel(), fieldConvertContext.getOriginValueConverter(), sourceFieldStrValue);
            originValue = tempCache.get(tempCacheKey);
            if (originValue == null && CacheLevel.isContainLocal(fieldConvert.cacheLevel())) {
                //本地缓存的不放入临时缓存中
                String originValueConverter = fieldConvertContext.getOriginValueConverter();
                GetParam getParam = new GetParam();
                getParam.setKey(sourceFieldStrValue);
                getParam.setOriginCacheName(getCacheName(originValueConverter));
                getParam.setAsync(false);
                getParam.setWrapperCacheName(getWrapperCacheName(originValueConverter));
                originValue = getCacheOperate(originValueConverter).getLocalCacheOperate().get(getParam);
                if (originValue == null && tempCache.isCanExpire()) {
                    //临时缓存能够过期，就重新走一次查询
                    originValue = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, true, cacheQueryValue);
                }
            } else if (originValue == null && tempCache.isCanExpire()) {
                //临时缓存能够过期，就重新走一次查询
                originValue = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, false, cacheQueryValue);
            }
            //设置转换值
            this.setConvertFieldValue(originValue, fieldConvert.converter(), data, fieldConvertContext.getConvertField());
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


    public void convertSingleData(Object data, boolean cacheQueryValue) {
        try {
            for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
                //临时缓存查询不到，再从全局缓存和数据库查询
                String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
                Object originData = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, false, cacheQueryValue);
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

    /**
     * 获取原始数据，有序的（local->remote->db)
     *
     * @param excludeLocal 排除本地查村
     */

    private Object getOriginDataByOrderly(GetParam getParam,
                                          CacheLevel cacheLevel,
                                          String originValueConverter,
                                          boolean excludeLocal,
                                          boolean cacheQueryValue) {
        Object originData = null;
        LocalAndRemoteCacheOperate localAndRemoteCacheOperate = this.getCacheOperate(originValueConverter);
        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
            if (excludeLocal) {
                originData = localAndRemoteCacheOperate.getRemoteCacheOperate().get(getParam);
            } else {
                originData = localAndRemoteCacheOperate.get(getParam);
            }
        } else if (CacheLevel.local.equals(cacheLevel) && !excludeLocal) {
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
            originData = originValueObtain.getConvertOriginData(getParam.getKey());
            return originData;
        } finally {
            if (originData != null && cacheQueryValue) {
                PutParam putParam = new PutParam();
                putParam.setKey(getParam.getKey());
                putParam.setAsync(true);
                putParam.setCacheValue(originData);
                putParam.setOriginCacheName(getParam.getOriginCacheName());
                putParam.setWrapperCacheName(getParam.getWrapperCacheName());
                localAndRemoteCacheOperate.put(putParam);
            }
        }
    }

    private Object getOriginDataByOrderly(FieldConvertContext fieldConvertContext,
                                          String sourceFieldStrValue,
                                          boolean excludeLocal,
                                          boolean cacheQueryValue) {
        if (sourceFieldStrValue == null) {
            return null;
        }
        FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
        CacheLevel cacheLevel = fieldConvert.cacheLevel();
        String originValueConverter = fieldConvertContext.getOriginValueConverter();
        GetParam getParam = new GetParam();
        getParam.setKey(sourceFieldStrValue);
        getParam.setOriginCacheName(getCacheName(originValueConverter));
        getParam.setAsync(false);
        getParam.setWrapperCacheName(getWrapperCacheName(originValueConverter));
        return getOriginDataByOrderly(getParam,
                cacheLevel, fieldConvertContext.getOriginValueConverter(), excludeLocal, cacheQueryValue);
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
        if (value == null) {
            return;
        }
        // TODO
//        if (CacheLevel.localAndRemote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutLocalAndRemote(converter, fieldStrValue, value);
//        } else if (CacheLevel.local.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutLocal(converter, fieldStrValue, value);
//        } else if (CacheLevel.remote.equals(cacheLevel)) {
//            originValueObtainSupport.asyncPutRemote(converter, fieldStrValue, value);
//        }
    }

    public CacheLevel getTempCacheLevelKey(CacheLevel cacheLevel) {
//        if (CacheLevel.none.equals(cacheLevel)) {
//            return CacheLevel.none;
//        } else if (CacheLevel.local.equals(cacheLevel)) {
//            return CacheLevel.local;
//        } else {
//            return CacheLevel.remote;
//        }
        return cacheLevel;
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

    private String getCacheName(String converter) {
        String cacheName = cacheFieldConvertProperties.getCacheNames().get(converter);
        if (!StringUtils.hasText(cacheName)) {
            throw new IllegalArgumentException("converter没有配置缓存名称:" + converter);
        }
        return cacheName;
    }


}
