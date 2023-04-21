/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeparatorIcon {

    private List<ElementList> elementList;

    public List<ElementList> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementList> elementList) {
        this.elementList = elementList;
    }

}