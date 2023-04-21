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
public class WebActionConfComponent {

    private ActionConfs actionConfs;
    private String orderUrl;
    private String shoppingCartUrl;
    public void setActionConfs(ActionConfs actionConfs) {
         this.actionConfs = actionConfs;
     }
     public ActionConfs getActionConfs() {
         return actionConfs;
     }

    public void setOrderUrl(String orderUrl) {
         this.orderUrl = orderUrl;
     }
     public String getOrderUrl() {
         return orderUrl;
     }

    public void setShoppingCartUrl(String shoppingCartUrl) {
         this.shoppingCartUrl = shoppingCartUrl;
     }
     public String getShoppingCartUrl() {
         return shoppingCartUrl;
     }

}