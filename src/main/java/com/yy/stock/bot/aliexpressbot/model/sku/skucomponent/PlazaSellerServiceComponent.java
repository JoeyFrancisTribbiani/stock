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
public class PlazaSellerServiceComponent {

    private Warranty warranty;
    private boolean hasWarrantyInfo;
    public void setWarranty(Warranty warranty) {
         this.warranty = warranty;
     }
     public Warranty getWarranty() {
         return warranty;
     }

    public void setHasWarrantyInfo(boolean hasWarrantyInfo) {
         this.hasWarrantyInfo = hasWarrantyInfo;
     }
     public boolean getHasWarrantyInfo() {
         return hasWarrantyInfo;
     }

}