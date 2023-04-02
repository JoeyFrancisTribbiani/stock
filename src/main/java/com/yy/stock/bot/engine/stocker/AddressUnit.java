package com.yy.stock.bot.engine.stocker;

import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.dto.StockRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AddressUnit {
    @Resource
    protected GridDriverEngine driverEngine;
    @Resource
    protected CoreEngine coreEngine;
    protected StockRequest stockRequest;

    public AddressUnit(StockRequest stockRequest) {
        this.stockRequest = stockRequest;
    }

    public void addNewAddress() throws InterruptedException {
        log.info(coreEngine.getBotName() + "开始添加新地址.");
        log.info(coreEngine.getBotName() + "删除旧地址.");
        deleteOldAddress();
        log.info(coreEngine.getBotName() + "输入地址信息.");
        inputAddressInfo();
    }

    protected abstract void inputAddressInfo() throws InterruptedException;

    protected abstract void deleteOldAddress();
}
