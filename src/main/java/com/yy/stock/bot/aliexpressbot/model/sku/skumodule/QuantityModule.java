/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuantityModule {

    private boolean activity;
    private boolean displayBulkInfo;
    private Features features;
    private I18nMap i18nMap;
    private int id;
    private boolean lot;
    private String multiUnitName;
    private String name;
    private String oddUnitName;
    private int purchaseLimitNumMax;
    private int skuBulkDiscount;
    private int skuBulkOrder;
    private int totalAvailQuantity;

    public boolean getActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public boolean getDisplayBulkInfo() {
        return displayBulkInfo;
    }

    public void setDisplayBulkInfo(boolean displayBulkInfo) {
        this.displayBulkInfo = displayBulkInfo;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public I18nMap getI18nMap() {
        return i18nMap;
    }

    public void setI18nMap(I18nMap i18nMap) {
        this.i18nMap = i18nMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getLot() {
        return lot;
    }

    public void setLot(boolean lot) {
        this.lot = lot;
    }

    public String getMultiUnitName() {
        return multiUnitName;
    }

    public void setMultiUnitName(String multiUnitName) {
        this.multiUnitName = multiUnitName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOddUnitName() {
        return oddUnitName;
    }

    public void setOddUnitName(String oddUnitName) {
        this.oddUnitName = oddUnitName;
    }

    public int getPurchaseLimitNumMax() {
        return purchaseLimitNumMax;
    }

    public void setPurchaseLimitNumMax(int purchaseLimitNumMax) {
        this.purchaseLimitNumMax = purchaseLimitNumMax;
    }

    public int getSkuBulkDiscount() {
        return skuBulkDiscount;
    }

    public void setSkuBulkDiscount(int skuBulkDiscount) {
        this.skuBulkDiscount = skuBulkDiscount;
    }

    public int getSkuBulkOrder() {
        return skuBulkOrder;
    }

    public void setSkuBulkOrder(int skuBulkOrder) {
        this.skuBulkOrder = skuBulkOrder;
    }

    public int getTotalAvailQuantity() {
        return totalAvailQuantity;
    }

    public void setTotalAvailQuantity(int totalAvailQuantity) {
        this.totalAvailQuantity = totalAvailQuantity;
    }

}