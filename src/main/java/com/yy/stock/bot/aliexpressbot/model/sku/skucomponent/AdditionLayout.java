/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionLayout {

    private String displayCondition;
    private String componentId;
    private int distance;
    private String blockType;
    private String type;
    private String blockGroup;
    private String content;
    private boolean useInDeliveryOptionPanel;
    private String medusaKey;
    private int order;
}