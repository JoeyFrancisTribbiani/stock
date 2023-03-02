package com.yy.stock.scheduler;

import com.yy.stock.adaptor.amazon.dto.AmzOrdersAddressDTO;
import com.yy.stock.adaptor.amazon.entity.OrdersReport;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.bot.Bot;
import com.yy.stock.common.util.SpringUtil;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.BuyerAccountDTO;
import com.yy.stock.dto.PlatformDTO;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.SupplierDTO;
import com.yy.stock.entity.Status;
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
    public void startStockAsync(OrdersReport orderToStock, Status status) {
        System.out.println("进入 asyncServiceExecutor");
        if (statusService.setStockingStatus(status) == false) {
            return;
        }
        Bot bot;
        StockRequest stockRequest;
        try {
            BuyerAccountDTO buyer = buyerAccountService.getNewestBuyer();
            AmzOrdersAddressDTO ordersAddress = amzOrdersAddressService.getByOrderIdAndAuthId(orderToStock.getAmazonOrderId(), orderToStock.getAmazonAuthId());
            SupplierDTO supplier = supplierService.getByAmazonOrderInfo(orderToStock);
            PlatformDTO platform = platformService.getById(supplier.getPlatformId());
            bot = SpringUtil.getBean(platform.getBotBean());
            stockRequest = new StockRequest(status, orderToStock, platform, supplier, buyer, ordersAddress);
        } catch (Exception ex) {
            status.setStatus(StatusEnum.unstocked.ordinal());
            status.setLog(ex.toString() + ex.getStackTrace());
            statusService.save(status);
            log.error("调度初始化失败,ex:" + ex.getMessage());
            return;
        }
        bot.doStock(stockRequest);

        System.out.println(bot);
    }
}
