package com.lz.core.test.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：缓存controller
 *
 * @author luyi
 * @date 2021/4/14
 */
@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/get/{id}")
    public String get(@PathVariable String id) {
        return cacheService.getXxx(id, null);
    }

    @RequestMapping("/put/{id}")
    public String put(@PathVariable String id) {
        return cacheService.put(id);
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        cacheService.delete(id);
        return "true";
    }

    @RequestMapping("update/{username}")
    public User update(@PathVariable String username) {
        return cacheService.updateUser(new User(username, 666));
    }
}
