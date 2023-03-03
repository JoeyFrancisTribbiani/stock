package com.yy.stock.scheduler;

import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.bot.Bot;
import com.yy.stock.common.util.SpringUtil;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.Status;
import com.yy.stock.entity.Supplier;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StatusService;
import com.yy.stock.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockAsyncExecutor {
    @Autowired
    private StatusService statusService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PlatformService platformService;


    @Async("asyncServiceExecutor")
    public void startStockAsync(OrderItemAdaptorInfoDTO orderToStock, Status status) {
        System.out.println("进入 asyncServiceExecutor");
        if (statusService.setStockingStatus(status) == false) {
            return;
        }
        Bot bot;
        StockRequest stockRequest;
        try {
            BuyerAccount buyer = buyerAccountService.getNewestBuyer();
            AmzOrdersAddress ordersAddress = amzOrdersAddressService.getByOrderInfo(orderToStock);
            Supplier supplier = supplierService.getByAmazonOrderInfo(orderToStock);
            Platform platform = platformService.getById(supplier.getPlatformId());
            bot = SpringUtil.getBean(platform.getBotBean());
            stockRequest = new StockRequest(status, orderToStock, platform, supplier, buyer, ordersAddress);
        } catch (Exception ex) {
            status.setStatus(StatusEnum.unstocked.ordinal());
            status.setLog(ex.toString() + ex.getStackTrace());
            statusService.save(status);
            log.error("调度初始化失败,ex:" + ex.getMessage());
            return;
        }
        try {
            bot.doStock(stockRequest);
        } catch (Exception ex) {

            status.setStatus(StatusEnum.stockFailed.ordinal());
            status.setLog(ex.toString() + ex.getStackTrace());
            statusService.save(status);
            log.error("bot采购失败,ex:" + ex.getMessage());
        }

    }
}
