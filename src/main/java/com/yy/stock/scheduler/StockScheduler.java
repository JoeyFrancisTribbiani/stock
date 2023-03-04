package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StockStatusService;
import com.yy.stock.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

;

@Service
public class StockScheduler {
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
    @Autowired
    protected VisibleThreadPoolTaskExecutor executor;
    @Autowired
    private AmzOrderItemService amzOrderItemService;
    @Autowired
    private OrdersReportService ordersReportService;
    @Autowired
    private StockStatusService stockStatusService;
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

        List<StockInfoDTO> toStock = filterUnstockedOrderItems();

        distributedLocker.unlock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("本线程 解锁");

        schedule(toStock);
    }


    public List<StockInfoDTO> filterUnstockedOrderItems() {
        List<StockInfoDTO> toStock = new ArrayList<>();

        if (isBusy()) {
            return toStock;
        }

        List<OrderItemAdaptorInfoDTO> unshippedIn3Days = amzOrderItemService.get3DaysUnshipped();
        List<OrderItemAdaptorInfoDTO> unshippedIn9To3Days = ordersReportService.get9To3DaysUnshippedOrders();
        unshippedIn9To3Days.addAll(unshippedIn3Days);


        int count = 0;
        for (OrderItemAdaptorInfoDTO order :
                unshippedIn9To3Days) {
            StockStatus stockStatus = stockStatusService.getOrCreateByOrderItemInfo(order);
            if (stockStatus.getStatus() == StatusEnum.unstocked.ordinal()) {
                count++;
                if (count > capacity()) {
                    break;
                }

                stockStatus.setStatus(StatusEnum.stocking.ordinal());
                stockStatusService.save(stockStatus);

                StockInfoDTO stockInfo = new StockInfoDTO();
                stockInfo.setOrderItemAdaptorInfo(order);
                stockInfo.setStockStatus(stockStatus);

                toStock.add(stockInfo);
            }
        }
        return toStock;
    }

    public void schedule(List<StockInfoDTO> toStock) {
        for (StockInfoDTO stockInfo : toStock) {
            stockAsyncExecutor.startStockAsync(stockInfo.getOrderItemAdaptorInfo(), stockInfo.getStockStatus());
        }
    }

    public boolean isBusy() {
        return executor.isFull();
    }

    public int capacity() {
        return executor.getIdleCount();
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
