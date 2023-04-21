/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skumodule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TitleLayout {

    private String blockGroup;
    private String blockType;
    private String componentId;
    private String content;
    private String displayCondition;
    private int distance;
    private String medusaKey;
    private int order;
    private String type;
    private boolean useInDeliveryOptionPanel;
    private boolean useInDetailFirstScreen;
    private boolean useInSkuSwitch;

}