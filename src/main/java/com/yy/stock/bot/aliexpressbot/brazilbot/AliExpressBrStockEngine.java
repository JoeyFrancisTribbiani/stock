package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.yy.stock.bot.aliexpressbot.engine.stocker.AliExpressStockEngine;

public class AliExpressBrStockEngine extends AliExpressStockEngine {
    @Override
    public void selectPaymentMethod() throws InterruptedException {
        Thread.sleep(1000);
        var html = driverEngine.getDriver().getPageSource();
        if (html.contains("Your payment information is safe with us")) {
            var container = driverEngine.getExecutor().getByClassName("payment-container-body");
            var cardMethodInput = driverEngine.getExecutor().listByRelativeXpath(container, ".//input").get(2);
            var clickable = driverEngine.getExecutor().getByRelativeXpath(cardMethodInput, ".//..");
            Thread.sleep(3000);
            clickable.click();
//            cardMethodInput.sendKeys(Keys.SPACE);
//            cardMethodInput.get(2).click();
            var confirmButton = driverEngine.getExecutor().getByXpath("//*[@id=\"payment-botton-section\"]/button");
            Thread.sleep(3000);
            confirmButton.click();
            Thread.sleep(3000);
        }
    }
}
