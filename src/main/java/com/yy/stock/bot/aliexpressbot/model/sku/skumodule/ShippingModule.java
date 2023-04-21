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
public class ShippingModule {

    private String currencyCode;
    private Features features;
    private FreightCalculateInfo freightCalculateInfo;
    private GeneralFreightInfo generalFreightInfo;
    private boolean hbaFreeShipping;
    private boolean hbaFreight;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private long productId;
    private String regionCountryName;
    private boolean suppressFreightInvoke;
    private String userCountryCode;
    private String userCountryName;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public FreightCalculateInfo getFreightCalculateInfo() {
        return freightCalculateInfo;
    }

    public void setFreightCalculateInfo(FreightCalculateInfo freightCalculateInfo) {
        this.freightCalculateInfo = freightCalculateInfo;
    }

    public GeneralFreightInfo getGeneralFreightInfo() {
        return generalFreightInfo;
    }

    public void setGeneralFreightInfo(GeneralFreightInfo generalFreightInfo) {
        this.generalFreightInfo = generalFreightInfo;
    }

    public boolean getHbaFreeShipping() {
        return hbaFreeShipping;
    }

    public void setHbaFreeShipping(boolean hbaFreeShipping) {
        this.hbaFreeShipping = hbaFreeShipping;
    }

    public boolean getHbaFreight() {
        return hbaFreight;
    }

    public void setHbaFreight(boolean hbaFreight) {
        this.hbaFreight = hbaFreight;
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getRegionCountryName() {
        return regionCountryName;
    }

    public void setRegionCountryName(String regionCountryName) {
        this.regionCountryName = regionCountryName;
    }

    public boolean getSuppressFreightInvoke() {
        return suppressFreightInvoke;
    }

    public void setSuppressFreightInvoke(boolean suppressFreightInvoke) {
        this.suppressFreightInvoke = suppressFreightInvoke;
    }

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public String getUserCountryName() {
        return userCountryName;
    }

    public void setUserCountryName(String userCountryName) {
        this.userCountryName = userCountryName;
    }

}