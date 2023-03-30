package com.yy.stock.bot.lazadaui.model.cart;

@lombok.Data
public class AddCartRequestModel {
    private String itemId;
    private String skuId;
    private long quantity;
}