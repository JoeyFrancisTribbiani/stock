package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.yy.stock.entity.Status;
import com.yy.stock.service.StatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class StatusServiceTest {
    @Autowired
    private StatusService statusService;

    @Test
    void getOrCreate() {
        String mId = "A2Q3Y263D00KWC";
        String oId = "701-2153377-3217026";
        Status r = statusService.getOrCreate(mId, oId);
        Assert.notNull(r);
        Assert.notNull(r.getId());
    }
}