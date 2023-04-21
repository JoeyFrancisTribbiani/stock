/**
 * Copyright 2023 ab173.com
 */
package com.yy.stock.bot.aliexpressbot.model.sku.skucomponent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPropComponent {

    private List<Props> props;

    public List<Props> getProps() {
        return props;
    }

    public void setProps(List<Props> props) {
        this.props = props;
    }

}