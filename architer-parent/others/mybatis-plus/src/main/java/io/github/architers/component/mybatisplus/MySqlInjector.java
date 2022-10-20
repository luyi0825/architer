package io.github.architers.component.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo){
        ///继承原有方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        //注入新方法
        methodList.add(new BatchInsert());
        return methodList;
    }
}
