/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionModule {

    private ActionConfs actionConfs;
    private String addToCartUrl;
    private String aeOrderFrom;
    private boolean allowVisitorAddCart;
    private String cartDetailUrl;
    private long categoryId;
    private boolean comingSoon;
    private long companyId;
    private String confirmOrderUrl;
    private Features features;
    private String freightExt;
    private int id;
    private boolean invalidBuyNow;
    private int itemStatus;
    private boolean itemWished;
    private int itemWishedCount;
    private boolean localSeller;
    private String name;
    private boolean preSale;
    private long productId;
    private int rootCategoryId;
    private boolean showCoinAnim;
    private boolean showShareHeader;
    private long storeNum;
    private SwitchInfo switchInfo;
    private int totalAvailQuantity;
}