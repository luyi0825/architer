package io.github.architers.context.cache.fieldconvert;


/**
 * 根据原始数据转换成需要的值
 *
 * @param <T>
 */
public interface IFieldValueConvert<T> extends IFieldConvert {


    Object getConvertValue(T originData);

}
