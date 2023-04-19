package com.yy.stock.bot.lazadabot.model.cart;

@lombok.Data
public class CartAsyncRequestModel {
    private String operator;
    private Data data;
    private Linkage linkage;
    private Hierarchy hierarchy;

}