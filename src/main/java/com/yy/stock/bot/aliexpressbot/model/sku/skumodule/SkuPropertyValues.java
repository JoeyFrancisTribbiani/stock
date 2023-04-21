/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SkuPropertyValues {

    private String propertyValueDefinitionName;
    private String propertyValueDisplayName;
    private long propertyValueId;
    private long propertyValueIdLong;
    private String propertyValueName;
    private String skuPropertyImagePath;
    private String skuPropertyImageSummPath;
    private String skuPropertyTips;
    private String skuColorValue;
    private int skuPropertyValueShowOrder;
    private String skuPropertyValueTips;
}