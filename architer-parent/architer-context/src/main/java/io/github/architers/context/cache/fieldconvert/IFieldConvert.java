package io.github.architers.context.cache.fieldconvert;


import java.io.Serializable;

/**
 * 数据转换器
 *
 * @author luyi
 */
public interface IFieldConvert<T extends Serializable> extends IFieldValueConvert<T>,IFieldOriginDataObtain<T> {




}
