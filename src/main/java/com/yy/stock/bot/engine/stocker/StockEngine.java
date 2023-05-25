package com.yy.stock.bot.engine.stocker;

import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.common.exception.SupplierUnavailableException;
import com.yy.stock.dto.StockRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@Getter
//@Setter
//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public abstract class StockEngine implements PluggableEngine {
    protected GridDriverEngine driverEngine;
    protected CoreEngine coreEngine;
    protected LoginEngine loginEngine;
    protected AddressEngine addressEngine;
    protected StockRequest stockRequest;

    // 付款开关，打开后才点击付款按钮，否则跳过付款，直接保存状态为已备货
    @Value("${bot.paySwitch}")
    protected boolean paySwitch = true;

    public void stock(StockRequest stockRequest) throws MessagingException, IOException, InterruptedException {
        this.stockRequest = stockRequest;
        this.addressEngine.stockRequest = stockRequest;
        log.info(coreEngine.getBotName() + "开始下单操作.");

        var supplierId = stockRequest.getSupplier().getId();
        var supplier = coreEngine.getSupplierService().getById(supplierId);

        if (!supplier.getAvailable()) {
            log.info(coreEngine.getBotName() + "供应商开关未打开.");
            throw new SupplierUnavailableException();
        }

        loginEngine.login();
        addressEngine.addNewAddress();
        clickBuyNow();
        checkout();
        payNow();
        saveOrderId();
    }


    protected abstract void saveOrderId();

    protected abstract void payNow() throws InterruptedException;

    protected abstract void checkout() throws InterruptedException;

    protected abstract void clickBuyNow() throws InterruptedException;

    protected abstract void selectPaymentMethod() throws InterruptedException;

    public String getAmazonOrderId() {
        return stockRequest.getOrderInfo().getOrderid();
    }


    @Override
    public void plugIn(CoreEngine plugBaseEngine) {
        this.coreEngine = plugBaseEngine;
        this.driverEngine = plugBaseEngine.getDriverEngine();
        this.loginEngine = plugBaseEngine.getLoginEngine();
        this.addressEngine = plugBaseEngine.getAddressEngine();
    }
}
