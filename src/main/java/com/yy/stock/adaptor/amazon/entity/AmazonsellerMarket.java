package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "t_amazonseller_market")
public class AmazonsellerMarket implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卖家Sellerid
     */
//    @Id
//    @Column(name = "sellerid", nullable = false)
//    private String sellerid;
//
//    /**
//     * 站点ID
//     */
//    @Id
//    @Column(name = "marketplace_id", nullable = false)
//    private String marketplaceId;

    @EmbeddedId
    private AmazonsellerMarketUPK amazonsellerMarketUPK;
    /**
     * 国家编码
     */
    @Column(name = "country")
    private String country;

    /**
     * 站点英文名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 对应语言编码
     */
    @Column(name = "language")
    private String language;

    /**
     * 对应币种
     */
    @Column(name = "currency")
    private String currency;

    /**
     * 对应域名
     */
    @Column(name = "domain")
    private String domain;

    /**
     * 授权对应ID等同于Sellerid
     */
    @Column(name = "amazonauthid")
    private String amazonauthid;

    /**
     * 操作时间
     */
    @Column(name = "opttime")
    private Date opttime;

    /**
     * 操作人
     */
    @Column(name = "disable")
    private Boolean disable;

}
