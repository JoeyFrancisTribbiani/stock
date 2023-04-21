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
public class SellerGuaranteeComponent {

    private boolean hasOverseaWarehouse;
    private String warranyDetailJson;
    private String forcePromiseWarrantyJson;

    public boolean getHasOverseaWarehouse() {
        return hasOverseaWarehouse;
    }

    public void setHasOverseaWarehouse(boolean hasOverseaWarehouse) {
        this.hasOverseaWarehouse = hasOverseaWarehouse;
    }

    public String getWarranyDetailJson() {
        return warranyDetailJson;
    }

    public void setWarranyDetailJson(String warranyDetailJson) {
        this.warranyDetailJson = warranyDetailJson;
    }

    public String getForcePromiseWarrantyJson() {
        return forcePromiseWarrantyJson;
    }

    public void setForcePromiseWarrantyJson(String forcePromiseWarrantyJson) {
        this.forcePromiseWarrantyJson = forcePromiseWarrantyJson;
    }

}