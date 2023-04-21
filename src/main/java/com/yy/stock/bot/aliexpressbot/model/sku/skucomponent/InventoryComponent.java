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
public class InventoryComponent {

    private int totalQuantity;
    private int totalAvailQuantity;
    public void setTotalQuantity(int totalQuantity) {
         this.totalQuantity = totalQuantity;
     }
     public int getTotalQuantity() {
         return totalQuantity;
     }

    public void setTotalAvailQuantity(int totalAvailQuantity) {
         this.totalAvailQuantity = totalAvailQuantity;
     }
     public int getTotalAvailQuantity() {
         return totalAvailQuantity;
     }

}