/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.stock.entity.Platform;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliExpressSkuModule {
    private Platform platform;
    private ActionModule actionModule;
    private AePlusModule aePlusModule;
    private CommonModule commonModule;
    private DescriptionModule descriptionModule;
    private Features features;
    private FeedbackModule feedbackModule;
    private GroupShareModule groupShareModule;
    private ImageModule imageModule;
    private InstallmentModule installmentModule;
    private String name;
    private PreSaleModule preSaleModule;
    private String prefix;
    private PriceModule priceModule;
    private QuantityModule quantityModule;
    private RedirectModule redirectModule;
    private ShippingModule shippingModule;
    private SkuModule skuModule;
    private SpecsModule specsModule;
    private StoreModule storeModule;
    private TitleModule titleModule;
    private WebEnv webEnv;

}