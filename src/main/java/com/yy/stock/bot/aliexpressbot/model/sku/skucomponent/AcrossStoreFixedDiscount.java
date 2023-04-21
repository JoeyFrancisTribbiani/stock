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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcrossStoreFixedDiscount {

    private List<PromotionPanelDetailDTOList> promotionPanelDetailDTOList;
    private String displayType;
    private String promotionGroupTitle;
    public void setPromotionPanelDetailDTOList(List<PromotionPanelDetailDTOList> promotionPanelDetailDTOList) {
         this.promotionPanelDetailDTOList = promotionPanelDetailDTOList;
     }
     public List<PromotionPanelDetailDTOList> getPromotionPanelDetailDTOList() {
         return promotionPanelDetailDTOList;
     }

    public void setDisplayType(String displayType) {
         this.displayType = displayType;
     }
     public String getDisplayType() {
         return displayType;
     }

    public void setPromotionGroupTitle(String promotionGroupTitle) {
         this.promotionGroupTitle = promotionGroupTitle;
     }
     public String getPromotionGroupTitle() {
         return promotionGroupTitle;
     }

}