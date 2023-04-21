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
public class UITest {

    private String adaptExtension;
    private String showSkuItem;
    public void setAdaptExtension(String adaptExtension) {
         this.adaptExtension = adaptExtension;
     }
     public String getAdaptExtension() {
         return adaptExtension;
     }

    public void setShowSkuItem(String showSkuItem) {
         this.showSkuItem = showSkuItem;
     }
     public String getShowSkuItem() {
         return showSkuItem;
     }

}