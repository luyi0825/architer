package com.business.base.codemap.api;


import com.architecture.query.common.model.Pager;
import com.business.base.codemap.entity.CodeMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author luyi
 * 代码集对应的api接口层
 */
@RestController
@RequestMapping("/codeMapApi")
public interface CodeMapApi {

    /**
     * 添加代码集
     *
     * @param codeMap 添加的代码级信息
     */
    @PostMapping("/add")
    void add(@RequestBody @Validated(value = AddGroup.class) CodeMap codeMap);


    /**
     * 更新
     *
     * @param id      主键ID
     * @param codeMap 更新的代码集信息
     */
    @PostMapping("/update/{id}")
    void update(@PathVariable(name = "id") Long id, @Validated(UpdateGroup.class) @RequestBody CodeMap codeMap);

    /**
     * gguo 通过主键ID删除
     *
     * @param id 主键ID
     */
    @PostMapping("/delete/{id}")
    void delete(@PathVariable(name = "id") Long id);

    /**
     * 通过主键ID查找
     *
     * @param id 主键ID
     * @return 代码集信息
     */
    @GetMapping("get/{id}")
    CodeMap getById(@PathVariable(name = "id") long id);

    /**
     * 分页查询代码集合
     *
     * @param pager 分页信息
     * @return 分页代码集信息
     */
    @GetMapping("/page")
    List<CodeMap> page(Pager pager);

}
