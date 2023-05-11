package com.yy.stock.bot.aliexpressbot.singaporebot;

import com.yy.stock.bot.aliexpressbot.engine.stocker.AliExpressStockEngine;
import org.openqa.selenium.WebElement;

public class AliExpressSgpStockEngine extends AliExpressStockEngine {
    @Override
    public void selectPaymentMethod() throws InterruptedException {
        Thread.sleep(1000);
        var html = driverEngine.getDriver().getPageSource();
        if (html.contains("Your payment information is safe with us")) {
            var container = driverEngine.getExecutor().getByClassName("payment-container-body");
            WebElement clickable = null;
            var card = stockRequest.getStockStatus().getBuyer().getCreditCard();
            if (card == null || card.equals("")) {
                var cardMethodInput = driverEngine.getExecutor().listByRelativeXpath(container, ".//input").get(2);
                clickable = driverEngine.getExecutor().getByRelativeXpath(cardMethodInput, ".//..");
            } else {
                var cardNumberSpanList = driverEngine.getExecutor().listByRelativeXpath(container, ".//span");
                for (var cardNumberSpan : cardNumberSpanList) {
                    var cardNumber = cardNumberSpan.getText();
                    var pre4ChracterCard = card.substring(0, 4);
                    if (cardNumber != null && cardNumber.contains(pre4ChracterCard)) {
                        clickable = driverEngine.getExecutor().getByRelativeXpath(cardNumberSpan, ".//..");
                        break;
                    }
                }
            }

            Thread.sleep(1000);
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
