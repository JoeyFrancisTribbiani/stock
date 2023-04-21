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
public class CAINIAO_STANDARD {

    private boolean allowArouseLayer;
    private String shippingFeeText;
    private boolean xdays;
    private ServiceDetails serviceDetails;
    public void setAllowArouseLayer(boolean allowArouseLayer) {
         this.allowArouseLayer = allowArouseLayer;
     }
     public boolean getAllowArouseLayer() {
         return allowArouseLayer;
     }

    public void setShippingFeeText(String shippingFeeText) {
         this.shippingFeeText = shippingFeeText;
     }
     public String getShippingFeeText() {
         return shippingFeeText;
     }

    public void setXdays(boolean xdays) {
         this.xdays = xdays;
     }
     public boolean getXdays() {
         return xdays;
     }

    public void setServiceDetails(ServiceDetails serviceDetails) {
         this.serviceDetails = serviceDetails;
     }
     public ServiceDetails getServiceDetails() {
         return serviceDetails;
     }

}