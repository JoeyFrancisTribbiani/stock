/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BizData {

    private String deliveryOptionCode;
    private String deliveryProviderName;
    private List<Integer> leadTimeSort;
    private long patternId;
    private int discount;
    private String utParams;
    private String tracking;
    private String provider;
    private String itemShipFromType;
    private String itemScene;
    private String shipToCode;
    private String company;
    private String currency;
    private String deliveryDate;
    private String shipFrom;
    private int deliveryDayMax;
    private int guaranteedDeliveryTime;
    private String freightCommitDay;
    private String etaTraceId;
    private long itemId;
    private String solutionBusinessType;
    private String shippingFee;
    private int deliveryDayMin;
    private String guaranteedDeliveryTimeProviderName;
    private String showXDayDeliveryTips;
    private long solutionId;
    private String shipFromCode;
    private String deliveryProviderCode;
    private String shipTo;

}