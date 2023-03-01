package com.yy.stock.test;

import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.scheduler.StockScheduler;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.vo.BuyerAccountVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockSchedulerTest {

    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private StockScheduler stockScheduler;


    @Autowired
    private VisibleThreadPoolTaskExecutor executor;

    @Test
    void autoStock() {
        if (buyerAccountService.count() == 0) {
            for (int i = 0; i < 8; i++) {
                BuyerAccountVO vo = new BuyerAccountVO();
                vo.setEmail(i + "@demo.com");
                vo.setMobile("13432432432");
                vo.setUsername("username" + i);
                vo.setPlatformId(886456888L);
                vo.setId(1L + i);
                vo.setOrderCount(i);
                buyerAccountService.save(vo);
            }
        }
        stockScheduler.autoStock();
    }

    @Test
    void testMultiTask() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (executor.isFull()) {
                break;
            }
//            stockJob.doStock(i);
        }
        while (!executor.isEmpty()) {
            Thread.sleep(3000);
        }
    }

}