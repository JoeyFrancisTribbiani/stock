package com.yy.stock.adaptor.amazon.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("t_product_info")
public class ProductInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("asin")
    private String asin;

    @TableField("sku")
    private String sku;

    @TableField("marketplaceid")
    private String marketplaceid;

    @TableField("name")
    private String name;

    @TableField("openDate")
    private LocalDateTime openDate;

    @TableField("itemDimensions")
    private BigInteger itemDimensions;

    @TableField("pageDimensions")
    private BigInteger pageDimensions;

    @TableField("fulfillChannel")
    private String fulfillChannel;

    private String binding;

    @TableField("totalOfferCount")
    private Integer totalOfferCount;

    private String brand;

    private String manufacturer;

    private String pgroup;

    private String typename;

    private BigDecimal price;

    private BigInteger image;

    @TableField("parentMarketplace")
    private String parentMarketplace;

    @TableField("parentAsin")
    private String parentAsin;

    private Boolean isparent;

    private Date lastupdate;

    private Date createdate;

    @TableField("amazonAuthId")
    private BigInteger amazonAuthId;

    private Boolean invalid;

    private String oldid;

    @TableField("inSnl")
    private Boolean inSnl;

    @TableField("disable")
    private Boolean disable;

    @TableField("fnsku")
    private String fnsku;

    @TableField("pcondition")
    private String pcondition;


    @TableField("refreshtime")
    private LocalDateTime refreshtime;

    /**
     * BUYABLE	清单项目可以由购物者购买。此状态不适用于供应商列表。
     * DISCOVERABLE	商品信息对购物者可见。
     */
    @TableField("status")
    private String status;

}
