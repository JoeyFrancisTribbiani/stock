package com.yy.stock.bot.engine.stocker;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.common.exception.SupplierUnavailableException;
import com.yy.stock.dto.StockRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Getter
@Setter
public abstract class StockEngine {
    protected GridDriverEngine driverEngine;
    protected CoreEngine coreEngine;
    protected LoginEngine loginEngine;
    protected AddressUnit addressUnit;
    protected StockRequest stockRequest;

    // 付款开关，打开后才点击付款按钮，否则跳过付款，直接保存状态为已备货
    @Value("${bot.paySwitch}")
    protected boolean paySwitch;

    public StockEngine(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.driverEngine = coreEngine.getDriverEngine();
        this.loginEngine = coreEngine.getLoginEngine();
        this.addressUnit = coreEngine.getAddressUnit();
    }

    public void stock(StockRequest stockRequest) throws MessagingException, IOException, InterruptedException {
        this.stockRequest = stockRequest;
        log.info(coreEngine.getBotName() + "开始下单操作.");

        var supplier = stockRequest.getSupplier();
        if (!supplier.getAvailable()) {
            log.info(coreEngine.getBotName() + "供应商开关未打开.");
            throw new SupplierUnavailableException();
        }

        loginEngine.login();
        addressUnit.addNewAddress();
        clickBuyNow();
        checkout();
        payNow();
        saveOrderId();
    }

    protected abstract void saveOrderId();

    protected abstract void payNow() throws InterruptedException;

    protected abstract void checkout() throws InterruptedException;

    protected abstract void clickBuyNow() throws InterruptedException;

    public String getAmazonOrderId() {
        return stockRequest.getOrderInfo().getOrderid();
    }
}
