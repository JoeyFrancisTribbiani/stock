/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.ProductSKUPropertyList;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuComponent {

    private String selectedSkuAttr;
    private boolean hasSkuProperty;
    private List<ProductSKUPropertyList> productSKUPropertyList;
    private String skuPropertyJson;

    public String getSelectedSkuAttr() {
        return selectedSkuAttr;
    }

    public void setSelectedSkuAttr(String selectedSkuAttr) {
        this.selectedSkuAttr = selectedSkuAttr;
    }

    public boolean getHasSkuProperty() {
        return hasSkuProperty;
    }

    public void setHasSkuProperty(boolean hasSkuProperty) {
        this.hasSkuProperty = hasSkuProperty;
    }

    public List<ProductSKUPropertyList> getProductSKUPropertyList() {
        return productSKUPropertyList;
    }

    public void setProductSKUPropertyList(List<ProductSKUPropertyList> productSKUPropertyList) {
        this.productSKUPropertyList = productSKUPropertyList;
    }

    public String getSkuPropertyJson() {
        return skuPropertyJson;
    }

    public void setSkuPropertyJson(String skuPropertyJson) {
        this.skuPropertyJson = skuPropertyJson;
    }

}