package com.business.base.area.api;


import com.architecture.ultimate.cache.common.enums.LockType;
import com.business.base.area.entity.StandardArea;
import com.architecture.ultimate.cache.common.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
