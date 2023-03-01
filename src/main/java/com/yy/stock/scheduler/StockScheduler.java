package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.dto.OrdersReportDTO;
import com.yy.stock.adaptor.amazon.service.AmzOrderMainService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.dto.BuyerAccountDTO;
import com.yy.stock.dto.StockRequestDTO;
import com.yy.stock.dto.SupplierDTO;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StatusService;
import com.yy.stock.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class StockScheduler {
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
    @Autowired
    protected VisibleThreadPoolTaskExecutor executor;
    @Autowired
    private AmzOrderMainService amzOrderMainService;
    @Autowired
    private OrdersReportService ordersReportService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;
    @Autowired
    private SupplierService supplierService;

    @XxlJob(value = "stockJobHandler")
    public void stockXxlJobHandler() throws InterruptedException {
        distributedLocker.lock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("本线程 加锁成功，开始选择订单");

        chooseOrder();

        distributedLocker.unlock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("本线程 解锁");
    }

    public void chooseOrder() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }
    }


    public boolean isBusy() {
        return executor.isFull();
    }

    public void autoStock() {

//        List<AmzOrderMainDTO> unshiped = amzOrderMainService.getUnshiped();
//        for (AmzOrderMainDTO orderMainDTO : unshiped) {
        List<OrdersReportDTO> unshiped = ordersReportService.getUnshiped();
        for (OrdersReportDTO ordersReportDTO : unshiped) {
            BuyerAccountDTO buyer = buyerAccountService.getNewestBuyer();
            AmzOrdersAddressDTO ordersAddress = amzOrdersAddressService.getByOrderIdAndAuthId(ordersReportDTO.getAmazonOrderId(), ordersReportDTO.getAmazonAuthId());
            SupplierDTO supplier = supplierService.getByAmazonOrderInfo(ordersReportDTO);
            StockRequestDTO stockRequest = new StockRequestDTO(ordersReportDTO, supplier, buyer, ordersAddress);
            System.out.println(ordersAddress);
        }
    }


}
