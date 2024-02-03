package io.github.architers.expand.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

public interface MybatisPlusAbstactMethodLoader {

    List<AbstractMethod> load(Class<?> mapperClass, TableInfo tableInfo);

}
