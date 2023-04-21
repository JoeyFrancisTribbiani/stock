/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.bot.aliexpressbot.model.sku.skumodule.SkuPriceList;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceComponent {

    private String skuJson;
    private String vatDesc;
    private boolean displayBulkInfo;
    private List<SkuPriceList> skuPriceList;
    private DiscountPrice discountPrice;
    private OrigPrice origPrice;
    private String priceLocalConfig;
}