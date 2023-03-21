package com.yy.stock.scheduler;

import com.yy.stock.adaptor.amazon.entity.AmzOrdersAddress;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.bot.Bot;
import com.yy.stock.common.exception.NoIdelBuyerAccountException;
import com.yy.stock.common.util.RedissonDistributedLocker;
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

import java.util.Arrays;

@Service
@Slf4j
public class StockAsyncExecutor {
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
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
        log.info(getExecutorName(orderToStock) + " 进入 asyncServiceExecutor进行多线程下单");
        Bot bot = null;
        StockRequest stockRequest = null;
        BuyerAccount buyer = null;
        Platform platform = null;
        var buyerLockKey = "";
        try {
            AmzOrdersAddress ordersAddress = amzOrdersAddressService.getByOrderInfo(orderToStock);
            Supplier supplier = supplierService.getByAmazonOrderInfo(orderToStock);
            platform = platformService.getById(supplier.getPlatformId());

            buyerLockKey = "BUYER_LOCK_KEY-PLATFORM_ID-" + platform.getId();
            distributedLocker.lock(buyerLockKey);
            log.info(getExecutorName(orderToStock) + "平台" + platform.getName() + "买家账号加锁成功，开始选择空闲的买家账号下单");

            try {
                buyer = buyerAccountService.getLeastOrderCountAndNotBuyingBuyer(platform.getId());
                buyer.setInBuying(true);
                buyerAccountService.save(buyer);
            } catch (Exception exx) {
                log.info(getExecutorName(orderToStock) + " 未找到空闲买家账号.");
                throw new NoIdelBuyerAccountException(getExecutorName(orderToStock) + " 未找到空闲买家账号.");
            } finally {
                distributedLocker.unlock(buyerLockKey);
                log.info(getExecutorName(orderToStock) + "平台" + platform.getName() + "解锁，买家账号为：" + (buyer != null ? buyer.getEmail() : "空"));
            }

            bot = BotFactory.getBot(platform, buyer);
            stockStatus.setBuyerId(buyer.getId())
                    .setSupplierId(supplier.getId())
                    .setOrderItemId(orderToStock.getOrderItemId());
            stockStatusService.save(stockStatus);
            stockRequest = new StockRequest(stockStatus, orderToStock, platform, supplier, ordersAddress);
            bot.doStock(stockRequest);
        } catch (Exception ex) {
            stockStatus.setStatus(StatusEnum.stockFailed.name());
            stockStatus.setLog(ex + Arrays.toString(ex.getStackTrace()));
            stockStatusService.save(stockStatus);
            if (bot != null) {
                log.info(bot.getBotName() + "开始退出chromedriver.");
                bot.quitDriver();
            }
            if (buyer != null) {
                log.info(getExecutorName(orderToStock) + "开始设置buyer的进行状态为false.");
                buyer.setInBuying(false);
                buyerAccountService.save(buyer);
            }
            log.info(getExecutorName(orderToStock) + "bot下单失败,ex:" + ex.getMessage());
        }

    }


    public String getExecutorName(OrderItemAdaptorInfoDTO orderToStock) {
        return "ASYNC_EXECUTOR" + "__" + orderToStock.getOrderid() + "__" + orderToStock.getOrderItemId() + "：";
    }
}
