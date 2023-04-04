package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class ShopVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private String id;


    /**
     * 公司名称
     */
    private String name;


    /**
     * 备注
     */
    private String remark;


    /**
     * 邀请码
     */
    private String invitecode;


    /**
     * 受邀请码
     */
    private String fromcode;

    private String oldid;

    private String bossEmail;

}
