/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-04-21 12:4:52
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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