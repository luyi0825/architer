package com.business.base.codemap.api;

import com.architecture.ultimate.module.common.valid.group.AddGroup;
import com.architecture.ultimate.module.common.web.PostMapping;
import com.business.base.codemap.entity.CodeMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author luyi
 * 代码集对应的api接口层
 */
@RestController
@RequestMapping("/codeConvertApi")
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
     * @return 更新后的代码级信息
     */
    @PostMapping("/update/{id}")
    CodeMap update(@PathVariable(name = "id") Long id, @RequestBody CodeMap codeMap);

    /**
     * gguo 通过主键ID删除
     *
     * @param id 主键ID
     */
    @PostMapping("delete/{id}")
    void remove(@PathVariable(name = "id") Long id);




}
