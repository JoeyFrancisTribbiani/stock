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
public class SwitchInfo {

    private boolean showPropOuter;
    private boolean deliveryMigrate;
    private boolean skuUnavailableRevision;
    private boolean allowUSVisitorCart;
    private boolean showPropGroups;

    public boolean getShowPropOuter() {
        return showPropOuter;
    }

    public void setShowPropOuter(boolean showPropOuter) {
        this.showPropOuter = showPropOuter;
    }

    public boolean getDeliveryMigrate() {
        return deliveryMigrate;
    }

    public void setDeliveryMigrate(boolean deliveryMigrate) {
        this.deliveryMigrate = deliveryMigrate;
    }

    public boolean getSkuUnavailableRevision() {
        return skuUnavailableRevision;
    }

    public void setSkuUnavailableRevision(boolean skuUnavailableRevision) {
        this.skuUnavailableRevision = skuUnavailableRevision;
    }

    public boolean getAllowUSVisitorCart() {
        return allowUSVisitorCart;
    }

    public void setAllowUSVisitorCart(boolean allowUSVisitorCart) {
        this.allowUSVisitorCart = allowUSVisitorCart;
    }

    public boolean getShowPropGroups() {
        return showPropGroups;
    }

    public void setShowPropGroups(boolean showPropGroups) {
        this.showPropGroups = showPropGroups;
    }

}