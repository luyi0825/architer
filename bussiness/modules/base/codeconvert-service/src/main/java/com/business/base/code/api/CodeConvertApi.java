package com.business.base.code.api;

import com.architecture.ultimate.module.common.valid.group.AddGroup;
import com.business.base.code.entity.CodeConvert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/codeConvertApi")
public interface CodeConvertApi {
    /**
     * 添加代码集
     *
     * @param codeConvert 添加的代码级信息
     */
    @PostMapping("/add")
    void add(@RequestBody @Validated(value = AddGroup.class) CodeConvert codeConvert);

    /**
     * gguo 通过主键ID删除
     *
     * @param id 主键ID
     */
    @PostMapping("delete/{id}")
    void remove(@PathVariable(name = "id") Long id);

    /**
     * 更新
     *
     * @param id          主键ID
     * @param codeConvert 更新的代码集信息
     * @return 更新后的代码级信息
     */
    @PostMapping("/update/{id}")
    CodeConvert update(@PathVariable(name = "id") Long id, @RequestBody CodeConvert codeConvert);


}
