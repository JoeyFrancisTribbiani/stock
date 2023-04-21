/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

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
public class QuantityModule {

    private boolean activity;
    private boolean displayBulkInfo;
    //    private I18nMap i18nMap;
    private int id;
    private boolean lot;
    private String multiUnitName;
    private String name;
    private String oddUnitName;
    private int purchaseLimitNumMax;
    private int skuBulkDiscount;
    private int skuBulkOrder;
    private int totalAvailQuantity;

}