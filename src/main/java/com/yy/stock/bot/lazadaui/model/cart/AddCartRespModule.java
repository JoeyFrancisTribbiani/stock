/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.lazadaui.model.cart;

import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-02-16 22:28:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
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