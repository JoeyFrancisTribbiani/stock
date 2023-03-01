package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class AmazonsellerMarketVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 卖家Sellerid
     */
    @NotNull(message = "sellerid can not null")
    private String sellerid;


    /**
     * 站点ID
     */
    @NotNull(message = "marketplaceId can not null")
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
