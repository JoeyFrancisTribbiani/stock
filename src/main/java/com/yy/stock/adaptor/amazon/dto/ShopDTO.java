package com.yy.stock.adaptor.amazon.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ShopDTO implements Serializable {
    private static final long serialVersionUID = 1L;
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
