package com.yy.stock.adaptor.amazon.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class AmazonsellerMarketDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 卖家Sellerid
     */
    private String sellerid;


    /**
     * 站点ID
     */
    private String marketplaceId;


    /**
     * 国家编码
     */
    private String country;


    /**
     * 站点英文名称
     */
    private String name;


    /**
     * 对应语言编码
     */
    private String language;


    /**
     * 对应币种
     */
    private String currency;


    /**
     * 对应域名
     */
    private String domain;


    /**
     * 授权对应ID等同于Sellerid
     */
    private String amazonauthid;


    /**
     * 操作时间
     */
    private Date opttime;


    /**
     * 操作人
     */
    private Boolean disable;

}
