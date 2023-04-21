/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.OriginalLayoutResultList;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
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