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
public class RecommendComponent {

    private int similarPageSize;
    private String afChannel;
    private String similarScenario;
    private boolean hideStoreRecommend;
    private boolean hideTopSelling;
    public void setSimilarPageSize(int similarPageSize) {
         this.similarPageSize = similarPageSize;
     }
     public int getSimilarPageSize() {
         return similarPageSize;
     }

    public void setAfChannel(String afChannel) {
         this.afChannel = afChannel;
     }
     public String getAfChannel() {
         return afChannel;
     }

    public void setSimilarScenario(String similarScenario) {
         this.similarScenario = similarScenario;
     }
     public String getSimilarScenario() {
         return similarScenario;
     }

    public void setHideStoreRecommend(boolean hideStoreRecommend) {
         this.hideStoreRecommend = hideStoreRecommend;
     }
     public boolean getHideStoreRecommend() {
         return hideStoreRecommend;
     }

    public void setHideTopSelling(boolean hideTopSelling) {
         this.hideTopSelling = hideTopSelling;
     }
     public boolean getHideTopSelling() {
         return hideTopSelling;
     }

}