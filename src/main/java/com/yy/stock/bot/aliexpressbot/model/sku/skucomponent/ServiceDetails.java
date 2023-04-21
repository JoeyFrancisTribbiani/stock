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
public class ServiceDetails {

    private List<Details> details;
    private String title;
    public void setDetails(List<Details> details) {
         this.details = details;
     }
     public List<Details> getDetails() {
         return details;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

}