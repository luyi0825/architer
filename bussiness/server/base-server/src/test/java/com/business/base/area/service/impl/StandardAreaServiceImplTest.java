package com.business.base.area.service.impl;

import com.business.base.area.service.StandardAreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(value = SpringRunner.class)
public class StandardAreaServiceImplTest {

    private StandardAreaService standardAreaService;

    @Test
    public void findByParentId() {
        standardAreaService.findByParentId("init");
    }

    @Autowired(required = false)
    public void setStandardAreaService(StandardAreaService standardAreaService) {
        this.standardAreaService = standardAreaService;
    }
}