package com.yy.stock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;


@Data
public class SupplierVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;


    /**
     * 亚马逊sku
     */
    @NotNull(message = "amazonSku can not null")
    private String amazonSku;


    /**
     * 商品名称
     */
    @NotNull(message = "amazonName can not null")
    private String amazonName;


    /**
     * 商品asin
     */
    private String amazonAsin;


    /**
     * 货源平台
     */
    @NotNull(message = "platformId can not null")
    private Long platformId;


    /**
     * 货源链接
     */
    @NotNull(message = "url can not null")
    private String url;


    /**
     * 货源名称
     */
    @NotNull(message = "name can not null")
    private String name;


    /**
     * 货源备货天数
     */
    private Integer stockLimitation;


    /**
     * 货源配送时效
     */
    private Integer shipLimitation;


    /**
     * 货源店铺
     */
    @NotNull(message = "shopName can not null")
    private String shopName;


    /**
     * 是否启用货源
     */
    @NotNull(message = "available can not null")
    private Boolean available;


    /**
     * 是否本土货源
     */
    private Boolean mainland;


    /**
     * 货源价格
     */
    @NotNull(message = "price can not null")
    private Float price;


    /**
     * 市场ID
     */
    @NotNull(message = "marketplaceId can not null")
    private String marketplaceId;


    /**
     * 亚马逊店铺ID
     */
    @NotNull(message = "amazonAuthId can not null")
    private BigInteger amazonAuthId;

}
