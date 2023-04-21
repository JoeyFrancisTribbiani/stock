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
public class MiddleBannerModule {

    private String END_IN;
    private String DAYS;
    private String DAY;
    private String STARTS_IN;

    public String getEND_IN() {
        return END_IN;
    }

    public void setEND_IN(String END_IN) {
        this.END_IN = END_IN;
    }

    public String getDAYS() {
        return DAYS;
    }

    public void setDAYS(String DAYS) {
        this.DAYS = DAYS;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getSTARTS_IN() {
        return STARTS_IN;
    }

    public void setSTARTS_IN(String STARTS_IN) {
        this.STARTS_IN = STARTS_IN;
    }

}