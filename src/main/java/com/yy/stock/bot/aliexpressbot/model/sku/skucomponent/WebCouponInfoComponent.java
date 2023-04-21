/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebCouponInfoComponent {

    private boolean usingNewCouponApi;
    private List<CouponList> couponList;
    private PromotionPanelDTO promotionPanelDTO;
    private CouponAttributes couponAttributes;

    public boolean getUsingNewCouponApi() {
        return usingNewCouponApi;
    }

    public void setUsingNewCouponApi(boolean usingNewCouponApi) {
        this.usingNewCouponApi = usingNewCouponApi;
    }

    public List<CouponList> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponList> couponList) {
        this.couponList = couponList;
    }

    public PromotionPanelDTO getPromotionPanelDTO() {
        return promotionPanelDTO;
    }

    public void setPromotionPanelDTO(PromotionPanelDTO promotionPanelDTO) {
        this.promotionPanelDTO = promotionPanelDTO;
    }

    public CouponAttributes getCouponAttributes() {
        return couponAttributes;
    }

    public void setCouponAttributes(CouponAttributes couponAttributes) {
        this.couponAttributes = couponAttributes;
    }

}