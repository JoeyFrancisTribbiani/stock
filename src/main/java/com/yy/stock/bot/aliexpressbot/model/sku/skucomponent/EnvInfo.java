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
public class EnvInfo {

    private boolean domestic;
    private String traceId;
    private boolean crawler;
    public void setDomestic(boolean domestic) {
         this.domestic = domestic;
     }
     public boolean getDomestic() {
         return domestic;
     }

    public void setTraceId(String traceId) {
         this.traceId = traceId;
     }
     public String getTraceId() {
         return traceId;
     }

    public void setCrawler(boolean crawler) {
         this.crawler = crawler;
     }
     public boolean getCrawler() {
         return crawler;
     }

}