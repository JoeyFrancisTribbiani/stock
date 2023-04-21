/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionComponent {
    private boolean discountPromotion;
    private boolean activity;
    private int fixedPromotionLeftHours;
    private int discount;
    private boolean fixedFreeShipping;
    private boolean regularPriceActivity;
    private int fixedPromotionLeftSecs;
    private int promotionLeftMinutes;
    private int promotionLeftSecs;
    private String discountTips;
    private int fixedPromotionLeftMinutes;
    private boolean coinsEnough;
    private boolean fireDeals;
    private String purchaseLimitMaxTips;
    private boolean superDeals;
    private int fixedPromotionLeftDays;
    private int promotionLeftHours;
    private boolean hbaFreeShipping;
    private boolean showStockPrompt;
    private boolean enableMultiDiscount;
    private int purchaseLimitNumMax;
    private String discountRatioTips;
    private boolean displayIcon;
    private boolean preSale;
    private int promotionLeftDays;
    private boolean availableGroupShareActivity;
    private boolean coinPreSale;
    private boolean fixedDiscountPromotion;
    private boolean comingSoon;
    private boolean allProduct;
    private boolean memberPriceActivity;
}