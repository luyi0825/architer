package com.architecture.test.cache.put;

import com.architecture.test.cache.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class PutServiceImpl implements PutService {
    @Override
    public void onePut(UserInfo userInfo) {
        System.out.println("保存到数据库");
    }

    @Override
    public void twoPut(UserInfo userInfo) {
        System.out.println("保存到数据库");
    }

    @Override
    public UserInfo returnValue(UserInfo userInfo) {
        return userInfo.setPassword("test");
    }
}
