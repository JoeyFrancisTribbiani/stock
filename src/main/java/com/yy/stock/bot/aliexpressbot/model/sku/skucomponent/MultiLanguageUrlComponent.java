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
public class MultiLanguageUrlComponent {

    private List<StoreMultilanguageUrlList> storeMultilanguageUrlList;
    private String storeDetailUrl;
    private List<MultilanguageUrlList> multilanguageUrlList;
    private String itemDetailUrl;
    private String mSiteUrl;
    public void setStoreMultilanguageUrlList(List<StoreMultilanguageUrlList> storeMultilanguageUrlList) {
         this.storeMultilanguageUrlList = storeMultilanguageUrlList;
     }
     public List<StoreMultilanguageUrlList> getStoreMultilanguageUrlList() {
         return storeMultilanguageUrlList;
     }

    public void setStoreDetailUrl(String storeDetailUrl) {
         this.storeDetailUrl = storeDetailUrl;
     }
     public String getStoreDetailUrl() {
         return storeDetailUrl;
     }

    public void setMultilanguageUrlList(List<MultilanguageUrlList> multilanguageUrlList) {
         this.multilanguageUrlList = multilanguageUrlList;
     }
     public List<MultilanguageUrlList> getMultilanguageUrlList() {
         return multilanguageUrlList;
     }

    public void setItemDetailUrl(String itemDetailUrl) {
         this.itemDetailUrl = itemDetailUrl;
     }
     public String getItemDetailUrl() {
         return itemDetailUrl;
     }

    public void setMSiteUrl(String mSiteUrl) {
         this.mSiteUrl = mSiteUrl;
     }
     public String getMSiteUrl() {
         return mSiteUrl;
     }

}