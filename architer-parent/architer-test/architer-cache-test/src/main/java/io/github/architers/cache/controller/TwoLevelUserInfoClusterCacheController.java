package io.github.architers.cache.controller;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.ITwoLevelDelayEvictAgainCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/twoLevelUserInfoClusterCache")
public class TwoLevelUserInfoClusterCacheController {

    @Resource
    private ITwoLevelDelayEvictAgainCacheService twoLevelUserInfoClusterCacheService;

    @GetMapping ("/getUserInfoWithCache")
    public UserInfo getUserInfoWithCache(@RequestParam("username") String username) {
        return twoLevelUserInfoClusterCacheService.getUserInfoWithCache(username);
    }

}
