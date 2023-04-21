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
public class SiteInfoComponent {

    private String wholesaleSubServer;
    private boolean crawler;
    private String siteType;
    public void setWholesaleSubServer(String wholesaleSubServer) {
         this.wholesaleSubServer = wholesaleSubServer;
     }
     public String getWholesaleSubServer() {
         return wholesaleSubServer;
     }

    public void setCrawler(boolean crawler) {
         this.crawler = crawler;
     }
     public boolean getCrawler() {
         return crawler;
     }

    public void setSiteType(String siteType) {
         this.siteType = siteType;
     }
     public String getSiteType() {
         return siteType;
     }

}