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
public class RecommendModule {

    private String MORE_FROM_THIS_SELLER;
    private String YOU_MAY_LIKE;
    private String TOP_SELLING;
    private String FROM_OTHER_SELLER;
    private String VIEW_MORE_LINK;
    private String PRODUCT_SOLD;
    public void setMORE_FROM_THIS_SELLER(String MORE_FROM_THIS_SELLER) {
         this.MORE_FROM_THIS_SELLER = MORE_FROM_THIS_SELLER;
     }
     public String getMORE_FROM_THIS_SELLER() {
         return MORE_FROM_THIS_SELLER;
     }

    public void setYOU_MAY_LIKE(String YOU_MAY_LIKE) {
         this.YOU_MAY_LIKE = YOU_MAY_LIKE;
     }
     public String getYOU_MAY_LIKE() {
         return YOU_MAY_LIKE;
     }

    public void setTOP_SELLING(String TOP_SELLING) {
         this.TOP_SELLING = TOP_SELLING;
     }
     public String getTOP_SELLING() {
         return TOP_SELLING;
     }

    public void setFROM_OTHER_SELLER(String FROM_OTHER_SELLER) {
         this.FROM_OTHER_SELLER = FROM_OTHER_SELLER;
     }
     public String getFROM_OTHER_SELLER() {
         return FROM_OTHER_SELLER;
     }

    public void setVIEW_MORE_LINK(String VIEW_MORE_LINK) {
         this.VIEW_MORE_LINK = VIEW_MORE_LINK;
     }
     public String getVIEW_MORE_LINK() {
         return VIEW_MORE_LINK;
     }

    public void setPRODUCT_SOLD(String PRODUCT_SOLD) {
         this.PRODUCT_SOLD = PRODUCT_SOLD;
     }
     public String getPRODUCT_SOLD() {
         return PRODUCT_SOLD;
     }

}