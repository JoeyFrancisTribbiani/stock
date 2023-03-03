package com.yy.stock.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class SupplierDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;


    /**
     * 亚马逊sku
     */
    private String amazonSku;


    /**
     * 商品名称
     */
    private String amazonName;


    /**
     * 商品asin
     */
    private String amazonAsin;


    /**
     * 货源平台
     */
    private Long platformId;


    /**
     * 货源链接
     */
    private String url;


    /**
     * 货源名称
     */
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
    private String shopName;


    /**
     * 是否启用货源
     */
    private Boolean available;


    /**
     * 是否本土货源
     */
    private Boolean mainland;


    /**
     * 货源价格
     */
    private Float price;


    /**
     * 市场ID
     */
    private String marketplaceId;


    /**
     * 亚马逊店铺ID
     */
    private BigInteger amazonAuthId;

    private String apiShopId;
    private String apiSkuId;
    private String apiItemId;
    private Float maxShipFee;
}
