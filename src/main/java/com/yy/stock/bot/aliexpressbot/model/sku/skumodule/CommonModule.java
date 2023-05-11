/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CommonModule {

    private boolean activity;
    private List<String> aePlusTags;
    private long buyerAccountId;
    private long buyerAdminSeq;
    private long categoryId;
    private boolean choiceProduct;
    private boolean crawler;
    private String currencyCode;
    private String gagaDataSite;
    private I18nMap i18nMap;
    private int id;
    private String name;
    private boolean plaza;
    private boolean preSale;
    private long productId;
    private String productIdStr;
    private ProductTags productTags;
    private long sellerAdminSeq;
    private String tradeCurrencyCode;
    private String userCountryCode;
    private String userCountryName;
}