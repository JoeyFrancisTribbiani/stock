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
public class ExtraComponent {

    private String expressionExt;
    private String aeOrderFrom;
    private boolean showCoinAnim;
    private String abTrace;
    private UiTestInfo uiTestInfo;
    private String freightExt;
    private String seoTitle;
    private String detailPageUrl;
    private EnvInfo envInfo;
    private String complaintUrl;
    private String productDetailImageUrl;

    public String getExpressionExt() {
        return expressionExt;
    }

    public void setExpressionExt(String expressionExt) {
        this.expressionExt = expressionExt;
    }

    public String getAeOrderFrom() {
        return aeOrderFrom;
    }

    public void setAeOrderFrom(String aeOrderFrom) {
        this.aeOrderFrom = aeOrderFrom;
    }

    public boolean getShowCoinAnim() {
        return showCoinAnim;
    }

    public void setShowCoinAnim(boolean showCoinAnim) {
        this.showCoinAnim = showCoinAnim;
    }

    public String getAbTrace() {
        return abTrace;
    }

    public void setAbTrace(String abTrace) {
        this.abTrace = abTrace;
    }

    public UiTestInfo getUiTestInfo() {
        return uiTestInfo;
    }

    public void setUiTestInfo(UiTestInfo uiTestInfo) {
        this.uiTestInfo = uiTestInfo;
    }

    public String getFreightExt() {
        return freightExt;
    }

    public void setFreightExt(String freightExt) {
        this.freightExt = freightExt;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public void setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
    }

    public EnvInfo getEnvInfo() {
        return envInfo;
    }

    public void setEnvInfo(EnvInfo envInfo) {
        this.envInfo = envInfo;
    }

    public String getComplaintUrl() {
        return complaintUrl;
    }

    public void setComplaintUrl(String complaintUrl) {
        this.complaintUrl = complaintUrl;
    }

    public String getProductDetailImageUrl() {
        return productDetailImageUrl;
    }

    public void setProductDetailImageUrl(String productDetailImageUrl) {
        this.productDetailImageUrl = productDetailImageUrl;
    }

}