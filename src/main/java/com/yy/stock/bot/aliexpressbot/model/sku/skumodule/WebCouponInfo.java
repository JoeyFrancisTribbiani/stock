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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WebCouponInfo {

    private CouponAttributes couponAttributes;
    private List<String> couponList;
    private PromotionPanelDTO promotionPanelDTO;
    private boolean usingNewCouponApi;

    public CouponAttributes getCouponAttributes() {
        return couponAttributes;
    }

    public void setCouponAttributes(CouponAttributes couponAttributes) {
        this.couponAttributes = couponAttributes;
    }

    public List<String> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<String> couponList) {
        this.couponList = couponList;
    }

    public PromotionPanelDTO getPromotionPanelDTO() {
        return promotionPanelDTO;
    }

    public void setPromotionPanelDTO(PromotionPanelDTO promotionPanelDTO) {
        this.promotionPanelDTO = promotionPanelDTO;
    }

    public boolean getUsingNewCouponApi() {
        return usingNewCouponApi;
    }

    public void setUsingNewCouponApi(boolean usingNewCouponApi) {
        this.usingNewCouponApi = usingNewCouponApi;
    }

}