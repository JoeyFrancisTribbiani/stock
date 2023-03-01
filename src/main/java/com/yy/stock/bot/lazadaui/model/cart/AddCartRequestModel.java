package com.yy.stock.bot.lazadaui.model.cart;

import com.yy.stock.bot.base.Product;

@lombok.Data
public class AddCartRequestModel extends Product {
    private String itemId;
    private String skuId;
    private long quantity;
}