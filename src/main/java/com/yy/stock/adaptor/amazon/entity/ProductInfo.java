package com.yy.stock.adaptor.amazon.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品信息
 */
@Data
@Entity
@Table(name = "t_product_info")
public class ProductInfo {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "asin")
    private String asin;

    @Column(name = "sku")
    private String sku;

    @Column(name = "marketplaceid")
    private String marketplaceid;

    @Column(name = "name")
    private String name;

    @Column(name = "openDate")
    private Date openDate;

    @Column(name = "itemDimensions")
    private String itemDimensions;

    @Column(name = "pageDimensions")
    private String pageDimensions;

    @Column(name = "fulfillChannel")
    private String fulfillChannel;

    @Column(name = "binding")
    private String binding;

    @Column(name = "totalOfferCount")
    private Integer totalOfferCount;

    @Column(name = "brand")
    private String brand;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "pgroup")
    private String pgroup;

    @Column(name = "typename")
    private String typename;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "parentMarketplace")
    private String parentMarketplace;

    @Column(name = "parentAsin")
    private String parentAsin;

    @Column(name = "isparent")
    private Boolean isparent;

    @Column(name = "lastupdate")
    private Date lastupdate;

    @Column(name = "createdate")
    private Date createdate;

    @Column(name = "amazonAuthId")
    private String amazonAuthId;

    @Column(name = "invalid")
    private Boolean invalid;

    @Column(name = "oldid")
    private String oldid;

    @Column(name = "inSnl")
    private Boolean inSnl;

    @Column(name = "fnsku")
    private String fnsku;

    @Column(name = "pcondition")
    private String pcondition;

    @Column(name = "status")
    private String status;

    @Column(name = "disable")
    private Boolean disable;

    @Column(name = "refreshtime")
    private Date refreshtime;

}
