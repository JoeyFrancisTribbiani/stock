/**
  * Copyright 2023 ab173.com 
  */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;
import java.util.Date;

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
public class SellerComponent {

    private boolean boutiqueSeller;
    private boolean topRatedSeller;
    private Date formatOpenTime;
    private boolean spanishPlaza;
    private boolean ruConsignmentAndMarketPlace;
    private boolean esRetailOrConsignment;
    private int openedYear;
    private boolean plazaElectronicSeller;
    private boolean ruSelfOperation;
    private String storeURL;
    private String storeFeedbackURL;
    private String storeName;
    private boolean hasStore;
    private long openTime;
    private String localSellerTag;
    private boolean aeRuSelfOperation;
    private boolean showPlazaHeader;
    private boolean localSeller;
    private long adminSeq;
    private String encryptOwnerMemberId;
    private String countryCompleteName;
    private long companyId;
    private String storeLogo;
    private long storeNum;
    private boolean payPalAccount;
    public void setBoutiqueSeller(boolean boutiqueSeller) {
         this.boutiqueSeller = boutiqueSeller;
     }
     public boolean getBoutiqueSeller() {
         return boutiqueSeller;
     }

    public void setTopRatedSeller(boolean topRatedSeller) {
         this.topRatedSeller = topRatedSeller;
     }
     public boolean getTopRatedSeller() {
         return topRatedSeller;
     }

    public void setFormatOpenTime(Date formatOpenTime) {
         this.formatOpenTime = formatOpenTime;
     }
     public Date getFormatOpenTime() {
         return formatOpenTime;
     }

    public void setSpanishPlaza(boolean spanishPlaza) {
         this.spanishPlaza = spanishPlaza;
     }
     public boolean getSpanishPlaza() {
         return spanishPlaza;
     }

    public void setRuConsignmentAndMarketPlace(boolean ruConsignmentAndMarketPlace) {
         this.ruConsignmentAndMarketPlace = ruConsignmentAndMarketPlace;
     }
     public boolean getRuConsignmentAndMarketPlace() {
         return ruConsignmentAndMarketPlace;
     }

    public void setEsRetailOrConsignment(boolean esRetailOrConsignment) {
         this.esRetailOrConsignment = esRetailOrConsignment;
     }
     public boolean getEsRetailOrConsignment() {
         return esRetailOrConsignment;
     }

    public void setOpenedYear(int openedYear) {
         this.openedYear = openedYear;
     }
     public int getOpenedYear() {
         return openedYear;
     }

    public void setPlazaElectronicSeller(boolean plazaElectronicSeller) {
         this.plazaElectronicSeller = plazaElectronicSeller;
     }
     public boolean getPlazaElectronicSeller() {
         return plazaElectronicSeller;
     }

    public void setRuSelfOperation(boolean ruSelfOperation) {
         this.ruSelfOperation = ruSelfOperation;
     }
     public boolean getRuSelfOperation() {
         return ruSelfOperation;
     }

    public void setStoreURL(String storeURL) {
         this.storeURL = storeURL;
     }
     public String getStoreURL() {
         return storeURL;
     }

    public void setStoreFeedbackURL(String storeFeedbackURL) {
         this.storeFeedbackURL = storeFeedbackURL;
     }
     public String getStoreFeedbackURL() {
         return storeFeedbackURL;
     }

    public void setStoreName(String storeName) {
         this.storeName = storeName;
     }
     public String getStoreName() {
         return storeName;
     }

    public void setHasStore(boolean hasStore) {
         this.hasStore = hasStore;
     }
     public boolean getHasStore() {
         return hasStore;
     }

    public void setOpenTime(long openTime) {
         this.openTime = openTime;
     }
     public long getOpenTime() {
         return openTime;
     }

    public void setLocalSellerTag(String localSellerTag) {
         this.localSellerTag = localSellerTag;
     }
     public String getLocalSellerTag() {
         return localSellerTag;
     }

    public void setAeRuSelfOperation(boolean aeRuSelfOperation) {
         this.aeRuSelfOperation = aeRuSelfOperation;
     }
     public boolean getAeRuSelfOperation() {
         return aeRuSelfOperation;
     }

    public void setShowPlazaHeader(boolean showPlazaHeader) {
         this.showPlazaHeader = showPlazaHeader;
     }
     public boolean getShowPlazaHeader() {
         return showPlazaHeader;
     }

    public void setLocalSeller(boolean localSeller) {
         this.localSeller = localSeller;
     }
     public boolean getLocalSeller() {
         return localSeller;
     }

    public void setAdminSeq(long adminSeq) {
         this.adminSeq = adminSeq;
     }
     public long getAdminSeq() {
         return adminSeq;
     }

    public void setEncryptOwnerMemberId(String encryptOwnerMemberId) {
         this.encryptOwnerMemberId = encryptOwnerMemberId;
     }
     public String getEncryptOwnerMemberId() {
         return encryptOwnerMemberId;
     }

    public void setCountryCompleteName(String countryCompleteName) {
         this.countryCompleteName = countryCompleteName;
     }
     public String getCountryCompleteName() {
         return countryCompleteName;
     }

    public void setCompanyId(long companyId) {
         this.companyId = companyId;
     }
     public long getCompanyId() {
         return companyId;
     }

    public void setStoreLogo(String storeLogo) {
         this.storeLogo = storeLogo;
     }
     public String getStoreLogo() {
         return storeLogo;
     }

    public void setStoreNum(long storeNum) {
         this.storeNum = storeNum;
     }
     public long getStoreNum() {
         return storeNum;
     }

    public void setPayPalAccount(boolean payPalAccount) {
         this.payPalAccount = payPalAccount;
     }
     public boolean getPayPalAccount() {
         return payPalAccount;
     }

}