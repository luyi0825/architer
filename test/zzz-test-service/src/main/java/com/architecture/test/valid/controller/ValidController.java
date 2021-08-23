package com.architecture.test.valid.controller;


import com.architecture.context.valid.group.AddGroup;
import com.architecture.test.valid.vo.User;
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
    public void test(@Validated @RequestBody User user) {

    }

    public static void main(String[] args) {


        System.out.println((Integer.SIZE - 3) << 29);
        System.out.println(0 << 29);
        System.out.println((1 << 29) - 1);
        System.out.println(2 << 29);
        System.out.println(Integer.MAX_VALUE - 536870911);
    }


}
