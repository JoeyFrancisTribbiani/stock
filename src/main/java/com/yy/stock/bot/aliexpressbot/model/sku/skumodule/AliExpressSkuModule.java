/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.yy.stock.dto.SkuModuleBase;
import com.yy.stock.entity.Platform;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AliExpressSkuModule extends SkuModuleBase {
    private Platform platform;
    //    private ActionModule actionModule;
    private AePlusModule aePlusModule;
    private CommonModule commonModule;
    private DescriptionModule descriptionModule;
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