package io.github.architers.context.cache.fieldconvert;


/**
 * 根据原始数据转换成需要的值
 *
 * @param <T>
 */
public interface IFieldValueConvert<T>  {


    Object getConvertValue(T originData);

    /**
     * 得到转换器的名称
     */
    String getConverter();

}
