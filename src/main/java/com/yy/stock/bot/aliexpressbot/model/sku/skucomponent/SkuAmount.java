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
public class SkuAmount {

    private String currency;
    private String formatedAmount;
    private double value;
    public void setCurrency(String currency) {
         this.currency = currency;
     }
     public String getCurrency() {
         return currency;
     }

    public void setFormatedAmount(String formatedAmount) {
         this.formatedAmount = formatedAmount;
     }
     public String getFormatedAmount() {
         return formatedAmount;
     }

    public void setValue(double value) {
         this.value = value;
     }
     public double getValue() {
         return value;
     }

}