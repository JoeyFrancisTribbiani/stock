/**
  * Copyright 2023 ab173.com 
  */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;
import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
public class WebCouponInfoComponent {

    private boolean usingNewCouponApi;
    private List<CouponList> couponList;
    private PromotionPanelDTO promotionPanelDTO;
    private CouponAttributes couponAttributes;
    public void setUsingNewCouponApi(boolean usingNewCouponApi) {
         this.usingNewCouponApi = usingNewCouponApi;
     }
     public boolean getUsingNewCouponApi() {
         return usingNewCouponApi;
     }

    public void setCouponList(List<CouponList> couponList) {
         this.couponList = couponList;
     }
     public List<CouponList> getCouponList() {
         return couponList;
     }

    public void setPromotionPanelDTO(PromotionPanelDTO promotionPanelDTO) {
         this.promotionPanelDTO = promotionPanelDTO;
     }
     public PromotionPanelDTO getPromotionPanelDTO() {
         return promotionPanelDTO;
     }

    public void setCouponAttributes(CouponAttributes couponAttributes) {
         this.couponAttributes = couponAttributes;
     }
     public CouponAttributes getCouponAttributes() {
         return couponAttributes;
     }

}