/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.lazadabot.model.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCartRespModule {

    private boolean forceLogin;
    private boolean success;
    private String fromPage;
    private String icon;
    private List<String> errorItems;
    private String showRedDot;
    private String msgInfo;
    private int cartNum;
    private List<String> cookies;
    private List<AddItems> addItems;
}