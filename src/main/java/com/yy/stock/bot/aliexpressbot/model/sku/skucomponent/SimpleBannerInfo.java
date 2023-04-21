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
public class SimpleBannerInfo {

    private String backgroundUrl;
    private String atmosphereCode;
    private String bannerType;
    private String originalPrice;
    private String sloganUrl;
    private EndTimer endTimer;
    private int backgroundHeight;
    private String price;
    private MinPrice minPrice;
    private int activityStatus;
    private MaxPrice maxPrice;
    private String salesText;
    public void setBackgroundUrl(String backgroundUrl) {
         this.backgroundUrl = backgroundUrl;
     }
     public String getBackgroundUrl() {
         return backgroundUrl;
     }

    public void setAtmosphereCode(String atmosphereCode) {
         this.atmosphereCode = atmosphereCode;
     }
     public String getAtmosphereCode() {
         return atmosphereCode;
     }

    public void setBannerType(String bannerType) {
         this.bannerType = bannerType;
     }
     public String getBannerType() {
         return bannerType;
     }

    public void setOriginalPrice(String originalPrice) {
         this.originalPrice = originalPrice;
     }
     public String getOriginalPrice() {
         return originalPrice;
     }

    public void setSloganUrl(String sloganUrl) {
         this.sloganUrl = sloganUrl;
     }
     public String getSloganUrl() {
         return sloganUrl;
     }

    public void setEndTimer(EndTimer endTimer) {
         this.endTimer = endTimer;
     }
     public EndTimer getEndTimer() {
         return endTimer;
     }

    public void setBackgroundHeight(int backgroundHeight) {
         this.backgroundHeight = backgroundHeight;
     }
     public int getBackgroundHeight() {
         return backgroundHeight;
     }

    public void setPrice(String price) {
         this.price = price;
     }
     public String getPrice() {
         return price;
     }

    public void setMinPrice(MinPrice minPrice) {
         this.minPrice = minPrice;
     }
     public MinPrice getMinPrice() {
         return minPrice;
     }

    public void setActivityStatus(int activityStatus) {
         this.activityStatus = activityStatus;
     }
     public int getActivityStatus() {
         return activityStatus;
     }

    public void setMaxPrice(MaxPrice maxPrice) {
         this.maxPrice = maxPrice;
     }
     public MaxPrice getMaxPrice() {
         return maxPrice;
     }

    public void setSalesText(String salesText) {
         this.salesText = salesText;
     }
     public String getSalesText() {
         return salesText;
     }

}