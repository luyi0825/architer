package io.github.architers.component.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultMybatisPlusAbstactMethodLoader implements MybatisPlusAbstactMethodLoader {
    @Override
    public List<AbstractMethod> load(Class<?> mapperClass, TableInfo tableInfo) {
        return List.of(new InsertBatchMethod("insertBatch"));
    }
}
