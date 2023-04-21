/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.SkuPriceList;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
@Data
public class PriceComponent {

    private String skuJson;
    private String vatDesc;
    private boolean displayBulkInfo;
    private List<SkuPriceList> skuPriceList;
    private DiscountPrice discountPrice;
    private OrigPrice origPrice;
    private String priceLocalConfig;

    public String getSkuJson() {
        return skuJson;
    }

    public void setSkuJson(String skuJson) {
        this.skuJson = skuJson;
    }

    public String getVatDesc() {
        return vatDesc;
    }

    public void setVatDesc(String vatDesc) {
        this.vatDesc = vatDesc;
    }

    public boolean getDisplayBulkInfo() {
        return displayBulkInfo;
    }

    public void setDisplayBulkInfo(boolean displayBulkInfo) {
        this.displayBulkInfo = displayBulkInfo;
    }

    public List<SkuPriceList> getSkuPriceList() {
        return skuPriceList;
    }

    public void setSkuPriceList(List<SkuPriceList> skuPriceList) {
        this.skuPriceList = skuPriceList;
    }

    public DiscountPrice getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(DiscountPrice discountPrice) {
        this.discountPrice = discountPrice;
    }

    public OrigPrice getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(OrigPrice origPrice) {
        this.origPrice = origPrice;
    }

    public String getPriceLocalConfig() {
        return priceLocalConfig;
    }

    public void setPriceLocalConfig(String priceLocalConfig) {
        this.priceLocalConfig = priceLocalConfig;
    }

}