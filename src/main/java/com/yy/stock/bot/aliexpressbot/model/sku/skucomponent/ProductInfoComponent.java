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
public class ProductInfoComponent {

    private String idStr;
    private String oddUnitName;
    private String taobaoSellerName;
    private String src;
    private String subject;
    private int numberPerLot;
    private long adminSeq;
    private List<String> propGroups;
    private boolean fromTaobao;
    private boolean lot;
    private String multiCurrencyDisplayPrice;
    private boolean openOfferPriceRule;
    private double minPrice;
    private long id;
    private double maxPrice;
    private String multiUnitName;
    private int categoryId;
    public void setIdStr(String idStr) {
         this.idStr = idStr;
     }
     public String getIdStr() {
         return idStr;
     }

    public void setOddUnitName(String oddUnitName) {
         this.oddUnitName = oddUnitName;
     }
     public String getOddUnitName() {
         return oddUnitName;
     }

    public void setTaobaoSellerName(String taobaoSellerName) {
         this.taobaoSellerName = taobaoSellerName;
     }
     public String getTaobaoSellerName() {
         return taobaoSellerName;
     }

    public void setSrc(String src) {
         this.src = src;
     }
     public String getSrc() {
         return src;
     }

    public void setSubject(String subject) {
         this.subject = subject;
     }
     public String getSubject() {
         return subject;
     }

    public void setNumberPerLot(int numberPerLot) {
         this.numberPerLot = numberPerLot;
     }
     public int getNumberPerLot() {
         return numberPerLot;
     }

    public void setAdminSeq(long adminSeq) {
         this.adminSeq = adminSeq;
     }
     public long getAdminSeq() {
         return adminSeq;
     }

    public void setPropGroups(List<String> propGroups) {
         this.propGroups = propGroups;
     }
     public List<String> getPropGroups() {
         return propGroups;
     }

    public void setFromTaobao(boolean fromTaobao) {
         this.fromTaobao = fromTaobao;
     }
     public boolean getFromTaobao() {
         return fromTaobao;
     }

    public void setLot(boolean lot) {
         this.lot = lot;
     }
     public boolean getLot() {
         return lot;
     }

    public void setMultiCurrencyDisplayPrice(String multiCurrencyDisplayPrice) {
         this.multiCurrencyDisplayPrice = multiCurrencyDisplayPrice;
     }
     public String getMultiCurrencyDisplayPrice() {
         return multiCurrencyDisplayPrice;
     }

    public void setOpenOfferPriceRule(boolean openOfferPriceRule) {
         this.openOfferPriceRule = openOfferPriceRule;
     }
     public boolean getOpenOfferPriceRule() {
         return openOfferPriceRule;
     }

    public void setMinPrice(double minPrice) {
         this.minPrice = minPrice;
     }
     public double getMinPrice() {
         return minPrice;
     }

    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setMaxPrice(double maxPrice) {
         this.maxPrice = maxPrice;
     }
     public double getMaxPrice() {
         return maxPrice;
     }

    public void setMultiUnitName(String multiUnitName) {
         this.multiUnitName = multiUnitName;
     }
     public String getMultiUnitName() {
         return multiUnitName;
     }

    public void setCategoryId(int categoryId) {
         this.categoryId = categoryId;
     }
     public int getCategoryId() {
         return categoryId;
     }

}