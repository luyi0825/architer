package com.business.base.area.service.impl;

import com.business.base.area.entity.StandardArea;
import com.business.base.area.service.StandardAreaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(value = SpringRunner.class)
public class StandardAreaServiceImplTest {

    private StandardAreaService standardAreaService;

    @Test
    public void findByParentId() {
        List<StandardArea> standardAreaList = standardAreaService.findByParentId(0);
        Assert.assertTrue(standardAreaList.size() > 0);
    }

    @Autowired(required = false)
    public void setStandardAreaService(StandardAreaService standardAreaService) {
        this.standardAreaService = standardAreaService;
    }
}