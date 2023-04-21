/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

/**
 * Auto-generated: 2023-04-21 12:11:30
 *
 * @author ab173.com (info@ab173.com)
 * @website http://www.ab173.com/json/
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceModule {

    private String LOT;
    private String PRE_ORDER_PRICE;
    private String INSTALLMENT;
    private String DEPOSIT;

    public String getLOT() {
        return LOT;
    }

    public void setLOT(String LOT) {
        this.LOT = LOT;
    }

    public String getPRE_ORDER_PRICE() {
        return PRE_ORDER_PRICE;
    }

    public void setPRE_ORDER_PRICE(String PRE_ORDER_PRICE) {
        this.PRE_ORDER_PRICE = PRE_ORDER_PRICE;
    }

    public String getINSTALLMENT() {
        return INSTALLMENT;
    }

    public void setINSTALLMENT(String INSTALLMENT) {
        this.INSTALLMENT = INSTALLMENT;
    }

    public String getDEPOSIT() {
        return DEPOSIT;
    }

    public void setDEPOSIT(String DEPOSIT) {
        this.DEPOSIT = DEPOSIT;
    }

}