package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存字段转换
 */
@Data
@ConfigurationProperties(prefix = "architers.cache.field-convert")
public class CacheFieldConvertProperties implements Serializable {

    /**
     * 本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> localOperateClass;

    /**
     * 远程缓存操作处理器
     */
    private Class<? extends RemoteCacheOperate> remoteOperateClass;

    /**
     * 是否打印转换总耗时
     */
    private boolean logTotalCost = true;

    /**
     * 转换的最大深度
     */
    private int maxDepth = 5;


    /**
     * 耗时多少打印warn日志（单位毫秒）
     */
    private long warnTotalCost = 5_1000;

    /**
     * 耗时多少会打印error日志（单位毫秒）
     */
    private long errorTotalCost = 10_1000;


    private Map<String/*converter*/, String/*缓存名称*/> cacheNames = new HashMap<>();


}
