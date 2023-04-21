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
public class SellerPromiseComponent {

    private List<String> returnPolicyGuarantees;
    private boolean hasOverseaWarehouse;
    private boolean hasNoReasonFreeReturn;
    private WarranyDetailMap warranyDetailMap;
    private ServiceDetails serviceDetails;
    private List<String> sellerGuaranteePromiseList;
    private ReturnPromise returnPromise;
    public void setReturnPolicyGuarantees(List<String> returnPolicyGuarantees) {
         this.returnPolicyGuarantees = returnPolicyGuarantees;
     }
     public List<String> getReturnPolicyGuarantees() {
         return returnPolicyGuarantees;
     }

    public void setHasOverseaWarehouse(boolean hasOverseaWarehouse) {
         this.hasOverseaWarehouse = hasOverseaWarehouse;
     }
     public boolean getHasOverseaWarehouse() {
         return hasOverseaWarehouse;
     }

    public void setHasNoReasonFreeReturn(boolean hasNoReasonFreeReturn) {
         this.hasNoReasonFreeReturn = hasNoReasonFreeReturn;
     }
     public boolean getHasNoReasonFreeReturn() {
         return hasNoReasonFreeReturn;
     }

    public void setWarranyDetailMap(WarranyDetailMap warranyDetailMap) {
         this.warranyDetailMap = warranyDetailMap;
     }
     public WarranyDetailMap getWarranyDetailMap() {
         return warranyDetailMap;
     }

    public void setServiceDetails(ServiceDetails serviceDetails) {
         this.serviceDetails = serviceDetails;
     }
     public ServiceDetails getServiceDetails() {
         return serviceDetails;
     }

    public void setSellerGuaranteePromiseList(List<String> sellerGuaranteePromiseList) {
         this.sellerGuaranteePromiseList = sellerGuaranteePromiseList;
     }
     public List<String> getSellerGuaranteePromiseList() {
         return sellerGuaranteePromiseList;
     }

    public void setReturnPromise(ReturnPromise returnPromise) {
         this.returnPromise = returnPromise;
     }
     public ReturnPromise getReturnPromise() {
         return returnPromise;
     }

}