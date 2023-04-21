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
public class ReturnPromise {

    private String brief;
    private String detailDescription;
    private String oldPromiseId;
    private String description;
    private int type;
    private String descriptionForSeller;
    private boolean isValuable;
    private String descriptionPretty;
    private String name;
    private int id;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getOldPromiseId() {
        return oldPromiseId;
    }

    public void setOldPromiseId(String oldPromiseId) {
        this.oldPromiseId = oldPromiseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescriptionForSeller() {
        return descriptionForSeller;
    }

    public void setDescriptionForSeller(String descriptionForSeller) {
        this.descriptionForSeller = descriptionForSeller;
    }

    public boolean getIsValuable() {
        return isValuable;
    }

    public void setIsValuable(boolean isValuable) {
        this.isValuable = isValuable;
    }

    public String getDescriptionPretty() {
        return descriptionPretty;
    }

    public void setDescriptionPretty(String descriptionPretty) {
        this.descriptionPretty = descriptionPretty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}