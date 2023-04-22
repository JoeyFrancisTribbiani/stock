package com.yy.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
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
    @JsonSerialize(using = ToStringSerializer.class)
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

    @ManyToOne
    @JoinColumn(name = "platform_id")
    @JsonIgnoreProperties(value = {"suppliers"}, allowSetters = true)
    private Platform platform;

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
    private String price;
    @Column(name = "price_buffer", nullable = false)
    private BigDecimal priceBuffer = BigDecimal.valueOf(0);

    @Column(name = "api_shop_id")
    private String apiShopId;
    @Column(name = "api_sku_id")
    private String apiSkuId;
    @Column(name = "api_item_id")
    private String apiItemId;
    @Column(name = "min_ship_fee")
    private String minShipFee;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "style_name")
    private String styleName;
}
//帮我根据这个实体类生成vue3的表单
