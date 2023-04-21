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
public class GeneralFreightInfo {

    private boolean allowArouseLayer;
    private boolean hideShipFrom;
    private List<OriginalLayoutResultList> originalLayoutResultList;
    private String shippingFeeText;
    private boolean usingNewFreight;

    public boolean getAllowArouseLayer() {
        return allowArouseLayer;
    }

    public void setAllowArouseLayer(boolean allowArouseLayer) {
        this.allowArouseLayer = allowArouseLayer;
    }

    public boolean getHideShipFrom() {
        return hideShipFrom;
    }

    public void setHideShipFrom(boolean hideShipFrom) {
        this.hideShipFrom = hideShipFrom;
    }

    public List<OriginalLayoutResultList> getOriginalLayoutResultList() {
        return originalLayoutResultList;
    }

    public void setOriginalLayoutResultList(List<OriginalLayoutResultList> originalLayoutResultList) {
        this.originalLayoutResultList = originalLayoutResultList;
    }

    public String getShippingFeeText() {
        return shippingFeeText;
    }

    public void setShippingFeeText(String shippingFeeText) {
        this.shippingFeeText = shippingFeeText;
    }

    public boolean getUsingNewFreight() {
        return usingNewFreight;
    }

    public void setUsingNewFreight(boolean usingNewFreight) {
        this.usingNewFreight = usingNewFreight;
    }

}