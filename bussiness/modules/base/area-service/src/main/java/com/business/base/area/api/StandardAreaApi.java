package com.business.base.area.api;


import com.architecture.ultimate.cache.common.annotation.Caching;
import com.architecture.ultimate.cache.common.annotation.DeleteCache;
import com.architecture.ultimate.cache.common.enums.LockType;
import com.business.base.area.entity.StandardArea;
import com.architecture.ultimate.cache.common.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luyi
 * 区划实体
 */
@RestController
@RequestMapping("/standardAreaApi")
public interface StandardAreaApi {
    /**
     * 通过parentId获取标准区划
     *
     * @param parentId 父级区划
     * @return parentId区划下的子区划
     */
    @GetMapping("/findByParentId/{parentId}")
    @Cacheable(cacheName = "'standardAreaApi_findByParentId'", key = "#parentId", lockType = LockType.read)
    List<StandardArea> findByParentId(@PathVariable("parentId") int parentId);

    /**
     * 通过主键ID查找区划
     *
     * @param id 主键ID
     * @return 标准区划信息
     */
    @GetMapping("/findById/{id}")
    @Cacheable(cacheName = "'standardAreaApi_findById'", key = "#id")
    StandardArea findById(@PathVariable("id") int id);

    /**
     * 更新区划
     *
     * @param standardArea 更新的区划信息
     */
    @PostMapping("update/{id}")
    @DeleteCache(cacheName = "'standardAreaApi_findById'", key = "#standardArea.id")
    void update(@RequestBody StandardArea standardArea);

    /**
     * 删除标准区划
     *
     * @param id 区划的主键ID
     */
    @PostMapping("delete/{id}")
    @Caching(
            delete = {@DeleteCache(cacheName = "'standardAreaApi_findByParentId'", key = "#id", before = false),
                    @DeleteCache(cacheName = "'standardAreaApi_findById'", key = "#id", before = false)},
            cacheable = {@Cacheable(cacheName = "'standardAreaApi_delete'", key = "#id")}
    )
    void delete(@PathVariable(name = "id") int id);
}
