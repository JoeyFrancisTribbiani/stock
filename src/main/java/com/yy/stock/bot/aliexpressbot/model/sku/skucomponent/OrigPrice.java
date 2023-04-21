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
public class OrigPrice {

    private MinAmount minAmount;
    private double minMultiCurrencyPrice;
    private double minPrice;
    private double maxPrice;
    private String multiCurrencyFormatPrice;
    private MaxAmount maxAmount;
    private String currencyFormatPrice;
    public void setMinAmount(MinAmount minAmount) {
         this.minAmount = minAmount;
     }
     public MinAmount getMinAmount() {
         return minAmount;
     }

    public void setMinMultiCurrencyPrice(double minMultiCurrencyPrice) {
         this.minMultiCurrencyPrice = minMultiCurrencyPrice;
     }
     public double getMinMultiCurrencyPrice() {
         return minMultiCurrencyPrice;
     }

    public void setMinPrice(double minPrice) {
         this.minPrice = minPrice;
     }
     public double getMinPrice() {
         return minPrice;
     }

    public void setMaxPrice(double maxPrice) {
         this.maxPrice = maxPrice;
     }
     public double getMaxPrice() {
         return maxPrice;
     }

    public void setMultiCurrencyFormatPrice(String multiCurrencyFormatPrice) {
         this.multiCurrencyFormatPrice = multiCurrencyFormatPrice;
     }
     public String getMultiCurrencyFormatPrice() {
         return multiCurrencyFormatPrice;
     }

    public void setMaxAmount(MaxAmount maxAmount) {
         this.maxAmount = maxAmount;
     }
     public MaxAmount getMaxAmount() {
         return maxAmount;
     }

    public void setCurrencyFormatPrice(String currencyFormatPrice) {
         this.currencyFormatPrice = currencyFormatPrice;
     }
     public String getCurrencyFormatPrice() {
         return currencyFormatPrice;
     }

}