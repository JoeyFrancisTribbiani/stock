package com.yy.stock.bot.businessbot;

import com.yy.stock.adaptor.amazon.api.pojo.vo.StockStatusListVo;

import java.math.BigDecimal;

public class BusinessBot {
    public static BigDecimal caculateProfit(StockStatusListVo supplierVo) {
        BigDecimal profit = BigDecimal.ZERO;
        var supplier = supplierVo.getSupplier();

        var marketplaceId = supplierVo.getMarketplaceid();

        var amazonSoldPrice = supplierVo.getChangeprice();
        var supplierPrice = new BigDecimal(supplier.getPrice());
        var supplierShippingFee = new BigDecimal(supplier.getMinShipFee());
        supplierPrice = supplierPrice.add(supplierShippingFee);

        var amazonSellTax = getAmazonSellTax(marketplaceId);
        var supplierBuyTax = getSupplierBuyTax(marketplaceId);

        var amazonTaxAmount = amazonSoldPrice.multiply(amazonSellTax);
        var supplierTaxAmount = supplierPrice.multiply(supplierBuyTax);

        var amazonEarnAmount = amazonSoldPrice.subtract(amazonTaxAmount);
        var supplierPayAmount = supplierPrice.add(supplierTaxAmount);

        profit = amazonEarnAmount.subtract(supplierPayAmount);

        return profit;
    }

    private static BigDecimal getAmazonSellTax(String marketplaceId) {
        //新加坡卖家id
        if (marketplaceId.equals("A19VAU5U5O7RUS")) {
            return new BigDecimal("0.07");
        }
        //巴西
        if (marketplaceId.equals("A2Q3Y263D00KWC")) {
            return new BigDecimal("0");
        }
        return BigDecimal.ZERO;
    }

    private static BigDecimal getSupplierBuyTax(String marketplaceId) {
        //新加坡卖家id
        if (marketplaceId.equals("A19VAU5U5O7RUS")) {
            return new BigDecimal("0.07");
        }
        //巴西
        if (marketplaceId.equals("A2Q3Y263D00KWC")) {
            return new BigDecimal("0");
        }
        return BigDecimal.ZERO;
    }
}
