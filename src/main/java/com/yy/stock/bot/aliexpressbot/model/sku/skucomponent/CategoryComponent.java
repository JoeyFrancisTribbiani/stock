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
public class CategoryComponent {

    private int topCategoryId;
    private boolean kidBaby;
    private long secondLevelCategoryId;

    public int getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(int topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public boolean getKidBaby() {
        return kidBaby;
    }

    public void setKidBaby(boolean kidBaby) {
        this.kidBaby = kidBaby;
    }

    public long getSecondLevelCategoryId() {
        return secondLevelCategoryId;
    }

    public void setSecondLevelCategoryId(long secondLevelCategoryId) {
        this.secondLevelCategoryId = secondLevelCategoryId;
    }

}