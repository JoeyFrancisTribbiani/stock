/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
public class SkuModule {

    private long categoryId;
    private Features features;
    private String forcePromiseWarrantyJson;
    private boolean hasSizeInfo;
    private boolean hasSkuProperty;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private List<ProductSKUPropertyList> productSKUPropertyList;
    private String selectedSkuAttr;
    private List<SkuPriceList> skuPriceList;
    private String warrantyDetailJson;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getForcePromiseWarrantyJson() {
        return forcePromiseWarrantyJson;
    }

    public void setForcePromiseWarrantyJson(String forcePromiseWarrantyJson) {
        this.forcePromiseWarrantyJson = forcePromiseWarrantyJson;
    }

    public boolean getHasSizeInfo() {
        return hasSizeInfo;
    }

    public void setHasSizeInfo(boolean hasSizeInfo) {
        this.hasSizeInfo = hasSizeInfo;
    }

    public boolean getHasSkuProperty() {
        return hasSkuProperty;
    }

    public void setHasSkuProperty(boolean hasSkuProperty) {
        this.hasSkuProperty = hasSkuProperty;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductSKUPropertyList> getProductSKUPropertyList() {
        return productSKUPropertyList;
    }

    public void setProductSKUPropertyList(List<ProductSKUPropertyList> productSKUPropertyList) {
        this.productSKUPropertyList = productSKUPropertyList;
    }

    public String getSelectedSkuAttr() {
        return selectedSkuAttr;
    }

    public void setSelectedSkuAttr(String selectedSkuAttr) {
        this.selectedSkuAttr = selectedSkuAttr;
    }

    public List<SkuPriceList> getSkuPriceList() {
        return skuPriceList;
    }

    public void setSkuPriceList(List<SkuPriceList> skuPriceList) {
        this.skuPriceList = skuPriceList;
    }

    public String getWarrantyDetailJson() {
        return warrantyDetailJson;
    }

    public void setWarrantyDetailJson(String warrantyDetailJson) {
        this.warrantyDetailJson = warrantyDetailJson;
    }

}