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
public class StoreHeaderResult {

    private List<TabList> tabList;
    private String allProductsText;
    private String storeName;
    public void setTabList(List<TabList> tabList) {
         this.tabList = tabList;
     }
     public List<TabList> getTabList() {
         return tabList;
     }

    public void setAllProductsText(String allProductsText) {
         this.allProductsText = allProductsText;
     }
     public String getAllProductsText() {
         return allProductsText;
     }

    public void setStoreName(String storeName) {
         this.storeName = storeName;
     }
     public String getStoreName() {
         return storeName;
     }

}