package com.business.base.area.service.impl;

import com.business.base.area.entity.StandardArea;
import com.business.base.area.service.StandardAreaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class StandardAreaServiceImplTest {

    private StandardAreaService standardAreaService;

    @Test
    public void findByParentId() {
        List<StandardArea> standardAreaList = standardAreaService.findByParentId(0);
        assertTrue(standardAreaList.size() > 0);
    }

    @Autowired(required = false)
    public void setStandardAreaService(StandardAreaService standardAreaService) {
        this.standardAreaService = standardAreaService;
    }
}