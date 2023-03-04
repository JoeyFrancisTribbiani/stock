package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * stock record from supplier
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    /**
     * 市场ID
     */
    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    /**
     * 亚马逊店铺ID
     */
    @Column(name = "amazon_auth_id", nullable = false)
    private BigInteger amazonAuthId;
    /**
     * 亚马逊sku
     */
    @Column(name = "amazon_sku", nullable = false)
    private String amazonSku;

    /**
     * 商品名称
     */
    @Column(name = "amazon_name", nullable = false)
    private String amazonName;

    /**
     * 商品asin
     */
    @Column(name = "amazon_asin")
    private String amazonAsin;

    /**
     * 货源平台
     */
    @Column(name = "platform_id", nullable = false)
    private BigInteger platformId;

    /**
     * 货源链接
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * 货源名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 货源备货天数
     */
    @Column(name = "stock_limitation")
    private Integer stockLimitation;

    /**
     * 货源配送时效
     */
    @Column(name = "ship_limitation")
    private Integer shipLimitation;

    /**
     * 货源店铺
     */
    @Column(name = "shop_name", nullable = false)
    private String shopName;

    /**
     * 是否启用货源
     */
    @Column(name = "is_available", nullable = false)
    private Boolean available;

    /**
     * 是否本土货源
     */
    @Column(name = "is_mainland")
    private Boolean mainland;

    /**
     * 货源价格
     */
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "api_shop_id")
    private String apiShopId;
    @Column(name = "api_sku_id")
    private String apiSkuId;
    @Column(name = "api_item_id")
    private String apiItemId;
    @Column(name = "max_ship_fee")
    private Float maxShipFee;
    @Column(name = "ui_style1_xpath")
    private String uiStyle1Xpath;
    @Column(name = "ui_style2_xpath")
    private String uiStyle2Xpath;
    @Column(name = "ui_style3_xpath")
    private String uiStyle3Xpath;
    @Column(name = "ui_amount_xpath")
    private String uiAmountXpath;
}
