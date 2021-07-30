package com.business.base.area.api;


import com.business.base.area.entity.StandardArea;
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
    List<StandardArea> findByParentId(@PathVariable("parentId") String parentId);
}
