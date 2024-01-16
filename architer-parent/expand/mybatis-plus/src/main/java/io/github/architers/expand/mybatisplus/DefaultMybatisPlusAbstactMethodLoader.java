package io.github.architers.component.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;


import java.util.List;

/**
 * @author luyi
 */
public class DefaultMybatisPlusAbstactMethodLoader implements MybatisPlusAbstactMethodLoader {
    @Override
    public List<AbstractMethod> load(Class<?> mapperClass, TableInfo tableInfo) {
        return List.of(new InsertBatchMethod());
    }
}
