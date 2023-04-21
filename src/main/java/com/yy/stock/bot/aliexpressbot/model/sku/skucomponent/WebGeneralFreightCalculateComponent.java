/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.OriginalLayoutResultList;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebGeneralFreightCalculateComponent {
    private boolean hideShipFrom;
    private boolean allowArouseLayer;
    private String shippingFeeText;
    private List<OriginalLayoutResultList> originalLayoutResultList;
    //    private DeliveryExtraInfoMap deliveryExtraInfoMap;
    private String freightExt;
    private String styleCode;
    private boolean usingNewFreight;

}