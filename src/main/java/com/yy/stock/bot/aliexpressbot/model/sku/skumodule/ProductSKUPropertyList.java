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
public class ProductSKUPropertyList {

    private boolean isShowTypeColor;
    private int order;
    private String showType;
    private boolean showTypeColor;
    private int skuPropertyId;
    private String skuPropertyName;
    private List<SkuPropertyValues> skuPropertyValues;

    public boolean getIsShowTypeColor() {
        return isShowTypeColor;
    }

    public void setIsShowTypeColor(boolean isShowTypeColor) {
        this.isShowTypeColor = isShowTypeColor;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public boolean getShowTypeColor() {
        return showTypeColor;
    }

    public void setShowTypeColor(boolean showTypeColor) {
        this.showTypeColor = showTypeColor;
    }

    public int getSkuPropertyId() {
        return skuPropertyId;
    }

    public void setSkuPropertyId(int skuPropertyId) {
        this.skuPropertyId = skuPropertyId;
    }

    public String getSkuPropertyName() {
        return skuPropertyName;
    }

    public void setSkuPropertyName(String skuPropertyName) {
        this.skuPropertyName = skuPropertyName;
    }

    public List<SkuPropertyValues> getSkuPropertyValues() {
        return skuPropertyValues;
    }

    public void setSkuPropertyValues(List<SkuPropertyValues> skuPropertyValues) {
        this.skuPropertyValues = skuPropertyValues;
    }

}