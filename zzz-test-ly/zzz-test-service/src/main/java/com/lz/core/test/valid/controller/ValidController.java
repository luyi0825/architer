package com.lz.core.test.valid.controller;


import com.lz.core.test.valid.vo.User;
import com.lz.core.valid.group.AddGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author luyi
 * Valid检验控制层测试
 */
@RestController
@RequestMapping("/validController")
public class ValidController {

    @PostMapping("/add")
    public User add(@Validated(value = {AddGroup.class}) @RequestBody User user) {
//        ValidResult validResult = ValidationUtil.validateBean(userVo, AddGroup.class);
//        String error = validResult.getErrors();
        return user;
    }


    @PostMapping
    public void test(@Validated @RequestBody User user){

    }


}
