package com.yy.stock.bot.engine.stocker;

import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.dto.StockRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AddressEngine implements PluggableEngine {
    protected GridDriverEngine driverEngine;
    protected CoreEngine coreEngine;
    protected StockRequest stockRequest;

    public void addNewAddress() throws InterruptedException {
        this.stockRequest = stockRequest;
        log.info(coreEngine.getBotName() + "开始添加新地址.");
        log.info(coreEngine.getBotName() + "删除旧地址.");
        deleteOldAddress();
        log.info(coreEngine.getBotName() + "输入地址信息.");
        inputAddressInfo();
    }

    protected abstract void inputAddressInfo() throws InterruptedException;

    protected abstract void deleteOldAddress();

    @Override
    public void plugIn(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.driverEngine = coreEngine.getDriverEngine();
        this.stockRequest = coreEngine.getStockEngine().getStockRequest();
    }
}
