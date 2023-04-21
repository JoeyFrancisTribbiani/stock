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
public class SkuVal {

    private List<String> optionalWarrantyPrice;
    private SkuAmount skuAmount;
    private String discount;
    private int availQuantity;
    private int inventory;
    private String skuCalPrice;
    private String skuAmountLocal;
    private String skuActivityAmountLocal;
    private String discountTips;
    private boolean isActivity;
    private SkuActivityAmount skuActivityAmount;
    public void setOptionalWarrantyPrice(List<String> optionalWarrantyPrice) {
         this.optionalWarrantyPrice = optionalWarrantyPrice;
     }
     public List<String> getOptionalWarrantyPrice() {
         return optionalWarrantyPrice;
     }

    public void setSkuAmount(SkuAmount skuAmount) {
         this.skuAmount = skuAmount;
     }
     public SkuAmount getSkuAmount() {
         return skuAmount;
     }

    public void setDiscount(String discount) {
         this.discount = discount;
     }
     public String getDiscount() {
         return discount;
     }

    public void setAvailQuantity(int availQuantity) {
         this.availQuantity = availQuantity;
     }
     public int getAvailQuantity() {
         return availQuantity;
     }

    public void setInventory(int inventory) {
         this.inventory = inventory;
     }
     public int getInventory() {
         return inventory;
     }

    public void setSkuCalPrice(String skuCalPrice) {
         this.skuCalPrice = skuCalPrice;
     }
     public String getSkuCalPrice() {
         return skuCalPrice;
     }

    public void setSkuAmountLocal(String skuAmountLocal) {
         this.skuAmountLocal = skuAmountLocal;
     }
     public String getSkuAmountLocal() {
         return skuAmountLocal;
     }

    public void setSkuActivityAmountLocal(String skuActivityAmountLocal) {
         this.skuActivityAmountLocal = skuActivityAmountLocal;
     }
     public String getSkuActivityAmountLocal() {
         return skuActivityAmountLocal;
     }

    public void setDiscountTips(String discountTips) {
         this.discountTips = discountTips;
     }
     public String getDiscountTips() {
         return discountTips;
     }

    public void setIsActivity(boolean isActivity) {
         this.isActivity = isActivity;
     }
     public boolean getIsActivity() {
         return isActivity;
     }

    public void setSkuActivityAmount(SkuActivityAmount skuActivityAmount) {
         this.skuActivityAmount = skuActivityAmount;
     }
     public SkuActivityAmount getSkuActivityAmount() {
         return skuActivityAmount;
     }

}