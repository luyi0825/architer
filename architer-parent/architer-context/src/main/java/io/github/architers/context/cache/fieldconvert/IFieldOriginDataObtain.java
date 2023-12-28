package io.github.architers.context.cache.fieldconvert;


import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 缓存原始数据获取(这个数据会被缓存到数据库中)
 *
 * @param <T>
 * @author luyi
 */
public interface IFieldOriginDataObtain<T extends Serializable> extends IFieldConvert {

    /**
     * 获取单个原始数据值
     */
    default T getConvertOriginData(String key) {
        throw new RuntimeException("not implement getConvertOriginData");
    }


    default Map<String, T> getConvertOriginDatas(Collection<String> keys) {
        throw new RuntimeException("not implement getConvertOriginDatas");
    }


}
