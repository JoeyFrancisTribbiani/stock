/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SkuPriceList {

    private String freightExt;
    private boolean salable;
    private String skuAttr;
    private long skuId;
    private String skuIdStr;
    private String skuPropIds;
    private SkuVal skuVal;

    public String getFreightExt() {
        return freightExt;
    }

    public void setFreightExt(String freightExt) {
        this.freightExt = freightExt;
    }

    public boolean getSalable() {
        return salable;
    }

    public void setSalable(boolean salable) {
        this.salable = salable;
    }

    public String getSkuAttr() {
        return skuAttr;
    }

    public void setSkuAttr(String skuAttr) {
        this.skuAttr = skuAttr;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getSkuIdStr() {
        return skuIdStr;
    }

    public void setSkuIdStr(String skuIdStr) {
        this.skuIdStr = skuIdStr;
    }

    public String getSkuPropIds() {
        return skuPropIds;
    }

    public void setSkuPropIds(String skuPropIds) {
        this.skuPropIds = skuPropIds;
    }

    public SkuVal getSkuVal() {
        return skuVal;
    }

    public void setSkuVal(SkuVal skuVal) {
        this.skuVal = skuVal;
    }

}