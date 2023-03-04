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
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.Supplier;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StockStatusService;
import com.yy.stock.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockAsyncExecutor {
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PlatformService platformService;


    @Async("asyncServiceExecutor")
    public void startStockAsync(OrderItemAdaptorInfoDTO orderToStock, StockStatus stockStatus) {
        System.out.println("进入 asyncServiceExecutor");
        // 设置备货中的状态
        if (stockStatusService.saveStatus(stockStatus, StatusEnum.stocking) == false) {
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
            stockRequest = new StockRequest(stockStatus, orderToStock, platform, supplier, buyer, ordersAddress);
        } catch (Exception ex) {
            stockStatus.setStatus(StatusEnum.unstocked.ordinal());
            stockStatus.setLog(ex.toString() + ex.getStackTrace());
            stockStatusService.save(stockStatus);
            log.error("调度初始化失败,ex:" + ex.getMessage());
            return;
        }
        try {
            bot.doStock(stockRequest);
        } catch (Exception ex) {

            stockStatus.setStatus(StatusEnum.stockFailed.ordinal());
            stockStatus.setLog(ex.toString() + ex.getStackTrace());
            stockStatusService.save(stockStatus);
            log.error("bot采购失败,ex:" + ex.getMessage());
        }

    }
}
