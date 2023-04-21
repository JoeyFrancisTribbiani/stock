/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreInfo {

    private String shippingSpeed;
    private String storeName;
    private String storeInfo;
    private String storeRating;
    private String storeId;
    private String businessAddress;
    private String communication;
    private String openSince;
    private String itemAsDescribed;
    private String businessInfo;

    public String getShippingSpeed() {
        return shippingSpeed;
    }

    public void setShippingSpeed(String shippingSpeed) {
        this.shippingSpeed = shippingSpeed;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
    }

    public String getStoreRating() {
        return storeRating;
    }

    public void setStoreRating(String storeRating) {
        this.storeRating = storeRating;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getOpenSince() {
        return openSince;
    }

    public void setOpenSince(String openSince) {
        this.openSince = openSince;
    }

    public String getItemAsDescribed() {
        return itemAsDescribed;
    }

    public void setItemAsDescribed(String itemAsDescribed) {
        this.itemAsDescribed = itemAsDescribed;
    }

    public String getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(String businessInfo) {
        this.businessInfo = businessInfo;
    }

}