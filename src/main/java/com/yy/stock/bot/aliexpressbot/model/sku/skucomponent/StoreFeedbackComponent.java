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
public class StoreFeedbackComponent {

    private int sellerScore;
    private int sellerTotalNum;
    private String sellerLevel;
    private String sellerPositiveRate;
    private int sellerPositiveNum;
    public void setSellerScore(int sellerScore) {
         this.sellerScore = sellerScore;
     }
     public int getSellerScore() {
         return sellerScore;
     }

    public void setSellerTotalNum(int sellerTotalNum) {
         this.sellerTotalNum = sellerTotalNum;
     }
     public int getSellerTotalNum() {
         return sellerTotalNum;
     }

    public void setSellerLevel(String sellerLevel) {
         this.sellerLevel = sellerLevel;
     }
     public String getSellerLevel() {
         return sellerLevel;
     }

    public void setSellerPositiveRate(String sellerPositiveRate) {
         this.sellerPositiveRate = sellerPositiveRate;
     }
     public String getSellerPositiveRate() {
         return sellerPositiveRate;
     }

    public void setSellerPositiveNum(int sellerPositiveNum) {
         this.sellerPositiveNum = sellerPositiveNum;
     }
     public int getSellerPositiveNum() {
         return sellerPositiveNum;
     }

}