/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BizData {

    private String deliveryOptionCode;
    private String deliveryProviderName;
    private List<Integer> leadTimeSort;
    private int patternId;
    private String logisticsComposeThreshold;
    private int discount;
    private String logisticsComposeCompany;
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
    private String meetLogisticsComposeThreshold;
    private long itemId;
    private String solutionBusinessType;
    private String logisticsComposeDeliveryDate;
    private String shippingFee;
    private int deliveryDayMin;
    private String guaranteedDeliveryTimeProviderName;
    private String showXDayDeliveryTips;
    private long solutionId;
    private String shipFromCode;
    private String deliveryProviderCode;
    private String shipTo;
}