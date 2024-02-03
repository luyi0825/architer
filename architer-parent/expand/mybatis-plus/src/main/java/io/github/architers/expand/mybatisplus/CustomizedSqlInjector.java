package io.github.architers.expand.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.ArrayList;
import java.util.List;

public class CustomizedSqlInjector extends DefaultSqlInjector {

    private MybatisPlusAbstactMethodLoader loader;

    public CustomizedSqlInjector(MybatisPlusAbstactMethodLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {

        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        List<AbstractMethod> selfList = loader.load(mapperClass, tableInfo);
        List<AbstractMethod> abstractMethods = new ArrayList<>(methodList.size() + selfList.size());
        abstractMethods.addAll(methodList);
        abstractMethods.addAll(selfList);
        return abstractMethods;
    }
}