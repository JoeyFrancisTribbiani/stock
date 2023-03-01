package com.yy.stock.bot.lazadaui.model.address;

import lombok.Data;

@Data
public class CreateAddressRequestModel {
    /**
     * Copyright 2023 json.cn
     */

    /**
     * Auto-generated: 2023-02-14 13:22:10
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */

    private String name;
    private String phone;
    private boolean loading;
    private String postCode;
    private String locationTreeAddressName;
    private String locationTreeAddressId;
    private String detailAddress;
    private String extendAddress;
}
