package com.yy.stock.bot.aliexpressbot.engine.stocker;

import cn.hutool.core.date.DateTime;
import com.yy.stock.bot.engine.stocker.StockEngine;
import com.yy.stock.common.exception.OverTopShipFeeException;
import com.yy.stock.common.exception.PayFailedException;
import com.yy.stock.common.exception.WrongStockPriceException;
import com.yy.stock.common.util.MySpringUtil;
import com.yy.stock.dto.StockStatusEnum;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StockStatusService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;

@Slf4j
//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public class AliExpressStockEngine extends StockEngine {
    protected PlatformService platformService;
    protected StockStatusService stockStatusService;
//    protected AliExpressBrCoreEngine coreEngine;
//    protected AliExpressLoginEngine loginEngine;
//    protected AliExpressBrAddressEngine addressUnit;

    public AliExpressStockEngine() {
        this.stockStatusService = MySpringUtil.getBean(StockStatusService.class);
        this.platformService = MySpringUtil.getBean(PlatformService.class);
    }

    private String generPlatformOrderPageUrl(String platformOrderId) {
        return "https://www.aliexpress.com/p/order/detail.html?orderId=" + platformOrderId;
    }

    @Override
    protected void saveOrderId() {
        var stockStatus = stockRequest.getStockStatus();
        try {
            var platformOrderId = getPlatformOrderId();
            stockStatus.setPlatformOrderId(platformOrderId);
            stockStatus.setPlatformOrderPageUrl(generPlatformOrderPageUrl(platformOrderId));
            stockStatusService.save(stockStatus);
        } catch (Exception ex) {
            stockStatus.setLog("付款后保存订单ID时出错!ex:" + ex.getMessage());
            stockStatus.setStatus(StockStatusEnum.payedButInfoSaveError.name());
            stockStatusService.save(stockStatus);
            log.error("付款后保存订单ID时出错! statusID:" + stockStatus.getId());
        }
    }


    @Override
    public void clickBuyNow() throws InterruptedException {
        var productPage = stockRequest.getSupplier().getUrl();
        driverEngine.getDriver().get(productPage);
        var styleList = stockRequest.getSupplier().getStyleName().split(",");
        var skuPanels = driverEngine.getExecutor().listByClassName("product-sku");
        for (int i = 0; i < styleList.length; i++) {
            var panel = skuPanels.get(i);
            var skuImgList = driverEngine.getExecutor().listByRelativeXpath(panel, ".//img");
            for (var img : skuImgList) {
                var styleTitle = img.getAttribute("title");
                if (styleTitle != null & styleTitle.equals(styleList[i])) {
                    img.click();
                    boolean isSelected = false;
                    while (!isSelected) {
                        var imgParentLi = driverEngine.getExecutor().getByRelativeXpath(img, ".//../..");
                        var className = imgParentLi.getAttribute("class");
                        if (className == null) {
                            continue;
                        }
                        if (className.contains("selected")) {
                            isSelected = true;
                        } else {
                            img.click();
                        }
                    }
                }
            }
        }

        WebElement amountDiv = driverEngine.getExecutor().getByClassName("product-quantity");
        var amountInput = driverEngine.getExecutor().getByRelativeXpath(amountDiv, ".//input");
        driverEngine.getExecutor().clearAndType(amountInput, stockRequest.getOrderInfo().getQuantity().toString());
        Thread.sleep(2333);

        var buyNowButtonDiv = driverEngine.getExecutor().getByClassName("product-action");
        var buyNowButton = driverEngine.getExecutor().getByRelativeXpath(buyNowButtonDiv, ".//button");
        buyNowButton.click();
        Thread.sleep(2333);
    }

    @Override
    public void checkout() throws InterruptedException {

        while (!driverEngine.getDriver().getCurrentUrl().contains(coreEngine.urls.confirmPaymentPage)) {
            log.info("等待进入confirm页面...");
            Thread.sleep(1000);
        }

        var quantityToBuy = BigDecimal.valueOf(stockRequest.getOrderInfo().getQuantity());

        WebElement orderSummaryPriceDiv = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.getSubtotalDiv());
        var text = orderSummaryPriceDiv.getText();
//        var subTotalPrice = coreEngine.parseUSDMoney(text);
        var subTotalPrice = coreEngine.parseMoney(text);

        var supplierPriceOdr = new BigDecimal(stockRequest.getSupplier().getPrice());
        var supplierPriceBuffer = stockRequest.getSupplier().getPriceBuffer();
        var supplierPrice = supplierPriceOdr.add(supplierPriceBuffer);
        supplierPrice = subTotalPrice.multiply(quantityToBuy);
        log.info(coreEngine.getBotName() + "供应商价格:$" + supplierPrice);
        log.info(coreEngine.getBotName() + "本次购买数量:" + quantityToBuy);
        log.info(coreEngine.getBotName() + "页面显示的价格:" + subTotalPrice);
        if (subTotalPrice.compareTo(supplierPrice) > 0) {
            throw new WrongStockPriceException("we got sub total price without ship fee:" + subTotalPrice + ", but the supplier price*quantity is:" + supplierPrice);
        }

        WebElement shippingFeeDiv = driverEngine.getExecutor().getByClassName("pl-summary__items");
        var items = driverEngine.getExecutor().listByRelativeXpath(shippingFeeDiv, ".//div");
        for (var item : items) {
            var divs = driverEngine.getExecutor().listByRelativeXpath(item, ".//div/div");
            if (divs != null && divs.size() >= 2) {
                var span = driverEngine.getExecutor().getByRelativeXpath(divs.get(0), ".//span");
                if (span != null && span.getText().equals("Total shipping")) {
                    var feeDiv = divs.get(1);
//                    var textDiv = driverEngine.getExecutor().getByRelativeXpath( feeDiv, ".//div");
                    text = feeDiv.getText();
                    text = text.equals("Free") ? "US $0" : text;
                    break;
                }
            }
        }
        var shippingFee = coreEngine.parseMoney(text);
        var minSupplierShippingFee = new BigDecimal(stockRequest.getSupplier().getMinShipFee());
        if (shippingFee.compareTo(minSupplierShippingFee) > 0) {
            throw new OverTopShipFeeException("we got a ship fee:" + shippingFee);
        }
    }

    public void payNow() throws InterruptedException {
        if (paySwitch) {
            WebElement totalPriceDiv = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.getTotalPriceDiv());
            var totalPrice = coreEngine.parseMoney(totalPriceDiv.getText());

            WebElement payNowButton = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.payNowButton);
            payNowButton.click();
            var succTips = "Payment Successful";
            int waitTick = 0;
            while (!driverEngine.getDriver().getPageSource().contains(succTips)) {
                if (waitTick > 66) {
                    throw new PayFailedException(coreEngine.getBotName() + "付款失败！请检查详情！");
                }
                log.info(coreEngine.getBotName() + "等待付款成功的提示...");
                waitTick++;
                Thread.sleep(1000);
            }
            log.info(coreEngine.getBotName() + "付款成功！");
            stockRequest.getStockStatus().setStatus(StockStatusEnum.stockedUnshipped.name());
            stockRequest.getStockStatus().setStockTime(DateTime.now());
            stockRequest.getStockStatus().setQuantity(stockRequest.getOrderInfo().getQuantity());
            stockRequest.getStockStatus().setTotalPrice(totalPrice);
            stockStatusService.save(stockRequest.getStockStatus());

        } else {
            throw new PayFailedException("付款开关未打开，实际没有付款！");
        }
    }

    private String getPlatformOrderId() {
        driverEngine.getDriver().get(coreEngine.urls.orderListPage);
        var firstOrderIdLabel = driverEngine.getExecutor().getByXpath(coreEngine.xpaths.firstOrderIdLabel);
        var text = firstOrderIdLabel.getText();
        var orderId = text.split(":")[1];
        orderId = orderId.split("\n")[0];
        orderId = orderId.strip();
        return orderId;
    }
}
