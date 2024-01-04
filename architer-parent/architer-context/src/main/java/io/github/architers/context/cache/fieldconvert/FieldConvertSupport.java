package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.CacheNameManager;
import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.fieldconvert.anantion.FieldConvert;
import io.github.architers.context.cache.fieldconvert.anantion.NeedFieldConvert;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperate;
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
 * 数据字段支持
 *
 * @author luyi
 * @since 1.0.1
 */
@Component
@Slf4j
public class FieldConvertSupport implements ApplicationContextAware {

    @Resource
    private CacheOperateManager cacheOperateManager;

    @Resource
    private CacheFieldConvertProperties cacheFieldConvertProperties;

    @Resource
    private CacheNameManager cacheNameManager;


    private final Map<String/*转换器名称*/, IFieldValueConvert<Serializable>> fieldValueConvertMap = new HashMap<>();

    private final Map<String, IFieldOriginDataObtain<Serializable>> fieldOriginDataObtainMap = new HashMap<>();


    public void convertData(Object data, ConvertFieldParam context) {
        long startTime = System.currentTimeMillis();
        int depth = 1;
        if (context.getTempCache() == null && data instanceof Collection) {
            if (((Collection<?>) data).size() > 1) {
                //集合默认使用HashMap的临时缓存
                context.setTempCache(new HashMapTempCache(((Collection<?>) data).size()));
            }
        }
        convertData(data, depth, false, context);
        long time = System.currentTimeMillis() - startTime;
        if (time > cacheFieldConvertProperties.getErrorTotalCost()) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }
    }

    /**
     * @param tempCacheParsed 临时缓存已经解析
     * @param data            转换的数据
     * @param context         字段转换上下文
     */
    private void convertData(Object data,
                             int depth,
                             boolean tempCacheParsed,
                             ConvertFieldParam context) {
        if (data == null) {
            return;
        }
        if (++depth > cacheFieldConvertProperties.getMaxDepth()) {
            throw new IllegalStateException("超过最大深度:" + cacheFieldConvertProperties.getMaxDepth());
        }
        if (data instanceof Collection) {
            convertCollectionData(data, depth, context);
        } else {
            //不是集合的就进行字段转换
            for (Field declaredField : data.getClass().getDeclaredFields()) {
                if (declaredField.getAnnotation(NeedFieldConvert.class) != null) {
                    try {
                        declaredField.setAccessible(true);
                        Object fieldData = declaredField.get(data);
                        if (fieldData != null) {
                            //递归转换
                            convertData(fieldData, depth, false, context);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (context.getTempCache() == null) {
                //转换单个数据
                convertSingleData(data, context);
            } else {
                if (!tempCacheParsed) {
                    parseTempCacheManyCacheValue(data, context);
                }
                //通过临时缓存转换单个数据
                convertSingeDataWithTempCache(data, context.getTempCache(), context);
            }

        }
    }

    private void convertCollectionData(Object data,
                                       int depth,
                                       ConvertFieldParam context) {
        if (CollectionUtils.isEmpty((Collection<?>) data)) {
            //数据为空
            return;
        }
        ++depth;
        parseTempCacheManyCacheValue(data, context);
        int finalDepth = depth;
        ((Collection<?>) data).forEach(e -> convertData(e, finalDepth, true, context));

    }


    private void parseTempCacheManyCacheValue(Object data, ConvertFieldParam context) {
        if (context.getTempCache() == null) {
            return;
        }
        Map<String/*converter*/, Map<CacheLevel, Set<String>>> converterCacheLevelKeyMap = this.parseConverterCacheLevelKeys(data);
        if (Boolean.FALSE.equals(context.getLocalCacheTemp())) {
            //临时缓存不缓存本地缓存的数据
            this.loadTempCacheDataWithExcludeLocalCache(context, converterCacheLevelKeyMap);
        } else {
            this.loadTempCacheDataWithIncludeLocalCache(context, converterCacheLevelKeyMap);
        }

    }

    private void loadTempCacheDataWithIncludeLocalCache(ConvertFieldParam context,
                                                        Map<String/*converter*/, Map<CacheLevel, Set<String>>> converterCacheLevelKeyMap) {
        TempCache tempCache = context.getTempCache();
        converterCacheLevelKeyMap.forEach((converter, cacheLevelMap) -> cacheLevelMap.forEach((cacheLevel, keys) -> {
            Map<String, Serializable> valueMap = null;
            Collection<String> notMatchKeys = null;
            LocalAndRemoteCacheOperate cacheOperate = this.getCacheOperate(converter);
            Set<String> notInTempCacheKeys = this.notInTempCacheKeys(keys, cacheLevel, converter, tempCache);
            if (CacheLevel.remote.equals(cacheLevel) || CacheLevel.none.equals(cacheLevel)) {
                //从db和remote获取之前，先判断本地有没有
                if (!CollectionUtils.isEmpty(notInTempCacheKeys)) {
                    if (CacheLevel.none.equals(cacheLevel)) {
                        //直接从DB
                        valueMap = getFieldOriginDataObtain(converter).getConvertOriginDatas(notInTempCacheKeys);
                    } else {
                        //从remote
                        BatchGetParam batchGetParam = new BatchGetParam();
                        batchGetParam.setKeys(notInTempCacheKeys);
                        batchGetParam.setWrapperCacheName(getWrapperCacheName(converter));
                        batchGetParam.setOriginCacheName(getCacheName(converter));
                        Map<String, Serializable> remoteValueMap = cacheOperate.getRemoteCacheOperate().batchGet(batchGetParam);
                        //取差集
                        notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(notInTempCacheKeys, remoteValueMap.keySet());
                        valueMap = remoteValueMap;
                    }
                }
            } else if (CacheLevel.localAndRemote.equals(cacheLevel)) {
                //从local
                BatchGetParam batchGetParam = new BatchGetParam();
                batchGetParam.setKeys(notInTempCacheKeys);
                batchGetParam.setWrapperCacheName(getWrapperCacheName(converter));
                batchGetParam.setOriginCacheName(getCacheName(converter));
                valueMap = cacheOperate.getLocalCacheOperate().batchGet(batchGetParam);
                //取差集
                if (notInTempCacheKeys.size() != valueMap.size()) {
                    //取差集
                    notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(notInTempCacheKeys, valueMap.keySet());
                    //本地缓存获取部分值
                    batchGetParam = new BatchGetParam();
                    batchGetParam.setKeys(new HashSet<>(notMatchKeys));
                    Map<String, Serializable> remoteValueMap = cacheOperate.getRemoteCacheOperate().batchGet(batchGetParam);
                    if (!CollectionUtils.isEmpty(remoteValueMap)) {
                        if (Collections.EMPTY_MAP.equals(valueMap)) {
                            //本地缓存没数据
                            valueMap = remoteValueMap;
                        } else {
                            //本地、远程都存在一定数据，合并一起
                            valueMap.putAll(remoteValueMap);
                        }
                    }
                    if (remoteValueMap.size() != notMatchKeys.size()) {
                        //remote中也没
                        notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(notInTempCacheKeys, valueMap.keySet());
                    }
                }
            } else if (CacheLevel.local.equals(cacheLevel)) {
                //从local
                BatchGetParam batchGetParam = new BatchGetParam();
                batchGetParam.setKeys(notInTempCacheKeys);
                batchGetParam.setWrapperCacheName(getWrapperCacheName(converter));
                batchGetParam.setOriginCacheName(getCacheName(converter));
                valueMap = cacheOperate.getLocalCacheOperate().batchGet(batchGetParam);
                //取差集
                if (notInTempCacheKeys.size() != valueMap.size()) {
                    //取差集
                    notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(notInTempCacheKeys, valueMap.keySet());
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
                if (!Boolean.FALSE.equals(context.getCacheNotHitValue())) {
                    //异步放到缓存
                    asyncPutCache(converter, dbMap);
                }
            }
        }));
    }

    private void loadTempCacheDataWithExcludeLocalCache(ConvertFieldParam context, Map<String/*converter*/, Map<CacheLevel, Set<String>>> converterCacheLevelKeyMap) {
        TempCache tempCache = context.getTempCache();
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
                        batchGetParam.setWrapperCacheName(getWrapperCacheName(converter));
                        batchGetParam.setOriginCacheName(getCacheName(converter));
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
                asyncPutCache(converter, dbMap);
            }
        }));
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
            //获取转换的值
            if (value instanceof IFieldValueConvert) {
                if (fieldValueConvertMap.putIfAbsent(value.getConverter(), (IFieldValueConvert<Serializable>) value) != null) {
                    throw new RuntimeException(value.getConverter() + "重复");
                }
            }
            //原始值获取
            if (value instanceof IFieldOriginDataObtain) {
                fieldOriginDataObtainMap.put(value.getConverter(), (IFieldOriginDataObtain<Serializable>) value);
            }
            //converter不能重复
            if (converters.contains(value.getConverter())) {
                throw new RuntimeException(value.getConverter() + "重复");
            }
            converters.add(value.getConverter());
        });
    }

    public IFieldOriginDataObtain<Serializable> getFieldOriginDataObtain(String converter) {
        return fieldOriginDataObtainMap.get(converter);
    }

    /**
     * 转换(数据会临时缓存）
     */
    public void convertSingeDataWithTempCache(Object data, TempCache tempCache, ConvertFieldParam context) {
        for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
            FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
            String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
            if (!StringUtils.hasText(sourceFieldStrValue)) {
                //值为空
                setConvertFieldValue(null, null, data, fieldConvertContext.getConvertField());
                continue;
            }
            Object originValue = null;
            if (!CacheLevel.local.equals(fieldConvert.cacheLevel())) {
                //不是本地缓存从临时缓存获取
                String tempCacheKey = getTempCacheKey(fieldConvert.cacheLevel(), fieldConvertContext.getOriginValueConverter(), sourceFieldStrValue);
                originValue = tempCache.get(tempCacheKey);
            }

            if (originValue == null
                    && Boolean.FALSE.equals(context.getLocalCacheTemp())
                    && CacheLevel.isContainLocal(fieldConvert.cacheLevel())) {
                //本地缓存的不放入临时缓存中
                String originValueConverter = fieldConvertContext.getOriginValueConverter();
                GetParam getParam = new GetParam();
                getParam.setKey(sourceFieldStrValue);
                getParam.setOriginCacheName(getCacheName(originValueConverter));
                getParam.setWrapperCacheName(getWrapperCacheName(originValueConverter));
                originValue = getCacheOperate(originValueConverter).getLocalCacheOperate().get(getParam);
                if (originValue == null) {
                    //临时缓存能够过期，就重新走一次查询
                    originValue = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, true, context);
                }
            } else if (originValue == null && tempCache.isCanEvict()) {
                //临时缓存能够过期，就重新走一次查询
                originValue = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, false, context);
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


    public void convertSingleData(Object data, ConvertFieldParam context) {
        try {
            for (FieldConvertContext fieldConvertContext : ClassFieldsCache.getFieldConvertContexts(data.getClass())) {
                //临时缓存查询不到，再从全局缓存和数据库查询
                String sourceFieldStrValue = getSourceFieldStrValue(fieldConvertContext, data);
                Object originData = this.getOriginDataByOrderly(fieldConvertContext, sourceFieldStrValue, false, context);
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
                IFieldValueConvert<Serializable> originValueConvert = fieldValueConvertMap.get(converter);
                if (originValueConvert == null) {
                    throw new RuntimeException("数据转换器为空:" + converter);
                }
                Object convertValue = originValueConvert.getConvertValue((Serializable) originData);
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
                                          ConvertFieldParam context) {
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
            if (originData != null && !Boolean.FALSE.equals(context.getCacheNotHitValue())) {
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
                                          ConvertFieldParam context) {
        if (sourceFieldStrValue == null) {
            return null;
        }
        FieldConvert fieldConvert = fieldConvertContext.getFieldConvert();
        CacheLevel cacheLevel = fieldConvert.cacheLevel();
        String originValueConverter = fieldConvertContext.getOriginValueConverter();
        GetParam getParam = new GetParam();
        getParam.setKey(sourceFieldStrValue);
        getParam.setOriginCacheName(getCacheName(originValueConverter));
        getParam.setWrapperCacheName(getWrapperCacheName(originValueConverter));
        return getOriginDataByOrderly(getParam, cacheLevel, fieldConvertContext.getOriginValueConverter(), excludeLocal, context);
    }

    public void asyncPutCache(String converter, Map<String, Serializable> cacheData) {
        if (CollectionUtils.isEmpty(cacheData)) {
            return;
        }
        //try起来，不影响正常业务
        try {
            BatchPutParam batchPutParam = new BatchPutParam();
            batchPutParam.setAsync(true);
            batchPutParam.setExpireTime(-1);
            batchPutParam.setBatchCacheValue(cacheData);
            batchPutParam.setWrapperCacheName(this.getWrapperCacheName(converter));
            batchPutParam.setOriginCacheName(this.getCacheName(converter));
            CacheOperate cacheOperate = getCacheOperate(converter);
            cacheOperate.batchPut(batchPutParam);
        } catch (Exception e) {
            log.warn("batchPut缓存失败", e);
        }

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
