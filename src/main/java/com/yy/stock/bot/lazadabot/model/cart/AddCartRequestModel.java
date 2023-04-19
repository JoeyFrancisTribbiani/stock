package com.yy.stock.bot.lazadabot.model.cart;

@lombok.Data
public class AddCartRequestModel {
    private String itemId;
    private String skuId;
    private long quantity;
}