/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SkuModule {

    private long categoryId;
    //    private String forcePromiseWarrantyJson;
    private boolean hasSizeInfo;
    private boolean hasSkuProperty;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private List<ProductSKUPropertyList> productSKUPropertyList;
    private String selectedSkuAttr;
    private List<SkuPriceList> skuPriceList;
    private String warrantyDetailJson;

}