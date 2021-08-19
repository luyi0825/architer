package com.business.base.codemap.api;


import com.architecture.context.common.cache.annotation.Cacheable;
import com.architecture.context.common.cache.enums.LockType;
import com.architecture.context.common.valid.group.AddGroup;
import com.architecture.context.common.valid.group.UpdateGroup;
import com.business.base.codemap.entity.CodeMapItem;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luyi
 * 代码项API接口层
 */
@RestController
@RequestMapping("/codeMapItemApi")
public interface CodeMapItemApi {

    /**
     * 添加
     *
     * @param codeMapItem 添加代码级项信息
     */
    @PostMapping("/add")
    void add(@RequestBody @Validated(AddGroup.class) CodeMapItem codeMapItem);

    /**
     * 更新
     *
     * @param codeMapItem 更新代码集项信息
     */
    @PostMapping("/update")
    void update(@RequestBody @Validated(UpdateGroup.class) CodeMapItem codeMapItem);


    /**
     * 通过code查询代码集项
     * <li>这个接口访问量比较大，开启重入锁，防止缓存击穿</li>
     *
     * @param code 代码级的code
     * @return 对应的代码集项
     */
    @GetMapping("/findByCode/{code}")
    @Cacheable(cacheName = "'codeMapItemApi_findByCode'", key = "#code", expireTime = 60 * 60 * 48, lockType = LockType.reentrant)
    List<CodeMapItem> findByCode(@PathVariable(name = "code") String code);


    /**
     * 通过ID查询
     *
     * @param id 主键ID
     * @return 代码级项信息
     */
    @GetMapping("/getById/{id}")
    @Cacheable(cacheName = "'codeMapItemApi_getById'", key = "#id", expireTime = 60 * 60 * 48, lockType = LockType.reentrant)
    CodeMapItem getById(@PathVariable(name = "id") Long id);


}
