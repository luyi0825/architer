package io.github.architers.cache.service;

import io.github.architers.cache.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public void saveNotExpired(UserInfo userInfo) {

    }

    @Override
    public void saveExpiredWithFiveSecond(UserInfo userInfo) {

    }

    @Override
    public void saveExpiredWithFiveMinute(UserInfo userInfo) {

    }

    @Override
    public UserInfo findByUsernameByCache(String username) {
        return null;
    }

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("查询数据库");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }

    @Override
    public UserInfo findByUsernameExpiredWithFiveSecond(String username) {
        System.out.println("查询数据库");
        return UserInfo.getRandomUserInfo().setUsername(username);
    }


}
