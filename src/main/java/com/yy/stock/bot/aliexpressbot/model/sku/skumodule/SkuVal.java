/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuVal {

    private int availQuantity;
    private int bulkOrder;
    private String discount;
    private String discountTips;
    private int inventory;
    private boolean isActivity;
    private List<String> optionalWarrantyPrice;
    private SkuActivityAmount skuActivityAmount;
    private SkuAmount skuAmount;
    private String skuCalPrice;

    public int getAvailQuantity() {
        return availQuantity;
    }

    public void setAvailQuantity(int availQuantity) {
        this.availQuantity = availQuantity;
    }

    public int getBulkOrder() {
        return bulkOrder;
    }

    public void setBulkOrder(int bulkOrder) {
        this.bulkOrder = bulkOrder;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountTips() {
        return discountTips;
    }

    public void setDiscountTips(String discountTips) {
        this.discountTips = discountTips;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public boolean getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(boolean isActivity) {
        this.isActivity = isActivity;
    }

    public List<String> getOptionalWarrantyPrice() {
        return optionalWarrantyPrice;
    }

    public void setOptionalWarrantyPrice(List<String> optionalWarrantyPrice) {
        this.optionalWarrantyPrice = optionalWarrantyPrice;
    }

    public SkuActivityAmount getSkuActivityAmount() {
        return skuActivityAmount;
    }

    public void setSkuActivityAmount(SkuActivityAmount skuActivityAmount) {
        this.skuActivityAmount = skuActivityAmount;
    }

    public SkuAmount getSkuAmount() {
        return skuAmount;
    }

    public void setSkuAmount(SkuAmount skuAmount) {
        this.skuAmount = skuAmount;
    }

    public String getSkuCalPrice() {
        return skuCalPrice;
    }

    public void setSkuCalPrice(String skuCalPrice) {
        this.skuCalPrice = skuCalPrice;
    }

}