package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.adaptor.amazon.service.AmzOrderMainService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.entity.Status;
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
    @Autowired
    private StockAsyncExecutor stockAsyncExecutor;

    @XxlJob(value = "stockJobHandler")
    public void stockXxlJobHandler() throws InterruptedException {
        distributedLocker.lock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("本线程 加锁成功，开始选择订单");

        schedule();

        distributedLocker.unlock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("本线程 解锁");
    }

    public void schedule() {
        List<OrdersReport> amazonUnshiped = ordersReportService.getUnshiped();
        for (OrdersReport order :
                amazonUnshiped) {
            if (isBusy()) {
                return;
            }
            Status status = statusService.getOrCreate(order.getMarketplaceId(), order.getAmazonOrderId());
            if (status.getStatus() == StatusEnum.unstocked.ordinal()) {
                stockAsyncExecutor.startStockAsync(order, status);
            }
        }

    }

    public boolean isBusy() {
        return executor.isFull();
    }

//    public void autoStock() {
//        List<OrdersReportDTO> unshiped = ordersReportService.getUnshiped();
//        for (OrdersReportDTO ordersReportDTO : unshiped) {
//            BuyerAccountDTO buyer = buyerAccountService.getNewestBuyer();
//            AmzOrdersAddressDTO ordersAddress = amzOrdersAddressService.getByOrderIdAndAuthId(ordersReportDTO.getAmazonOrderId(), ordersReportDTO.getAmazonAuthId());
//            SupplierDTO supplier = supplierService.getByAmazonOrderInfo(ordersReportDTO);
//            StockRequest stockRequest = new StockRequest(ordersReportDTO, supplier, buyer, ordersAddress);
//            System.out.println(ordersAddress);
//        }
//    }


}
