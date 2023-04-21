/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceModule {
    private boolean activity;
    private int discount;
    private boolean discountPromotion;
    private String discountRatioTips;
    private String discountTips;
    private ExtraTags extraTags;
    private Features features;
    private String formatedActivityPrice;
    private String formatedPrice;
    private boolean hiddenBigSalePrice;
    private I18nMap i18nMap;
    private int id;
    private boolean installment;
    private boolean lot;
    private MaxActivityAmount maxActivityAmount;
    private MaxAmount maxAmount;
    private MinActivityAmount minActivityAmount;
    private MinAmount minAmount;
    private String multiUnitName;
    private String name;
    private int numberPerLot;
    private String oddUnitName;
    private boolean preSale;
    private PriceRuleInfo priceRuleInfo;
    //    private List<String> promotionSellingPointTags;
    private boolean regularPriceActivity;
    private boolean showActivityMessage;
    private String vatDesc;

}