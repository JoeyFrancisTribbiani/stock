package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * purchase_order_number
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "t_orders_report")
public class OrdersReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    //    @Id
    @Column(name = "amazonAuthId", nullable = false)
    private BigInteger amazonAuthId;

    //    @Id
    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "merchant_order_id")
    private String merchantOrderId;


    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "fulfillment_channel")
    private String fulfillmentChannel;

    @Column(name = "sales_channel")
    private String salesChannel;

    @Column(name = "order_channel")
    private String orderChannel;

    @Column(name = "url")
    private String url;

    @Column(name = "ship_service_level")
    private String shipServiceLevel;

    @Column(name = "sku")
    private String sku;

    @Column(name = "asin")
    private String asin;

    @Column(name = "item_status")
    private String itemStatus;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "currency")
    private String currency;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @Column(name = "item_tax")
    private BigDecimal itemTax;

    @Column(name = "shipping_price")
    private BigDecimal shippingPrice;

    @Column(name = "shipping_tax")
    private BigDecimal shippingTax;

    @Column(name = "gift_wrap_price")
    private BigDecimal giftWrapPrice;

    @Column(name = "gift_wrap_tax")
    private BigDecimal giftWrapTax;

    @Column(name = "item_promotion_discount")
    private BigDecimal itemPromotionDiscount;

    @Column(name = "ship_promotion_discount")
    private BigDecimal shipPromotionDiscount;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_state")
    private String shipState;

    @Column(name = "ship_postal_code")
    private String shipPostalCode;

    @Column(name = "ship_country")
    private String shipCountry;

    @Column(name = "promotion_ids")
    private String promotionIds;

    @Column(name = "is_business_order")
    private Boolean businessOrder;

    @Column(name = "purchase_order_number")
    private String purchaseOrderNumber;

    @Column(name = "price_designation")
    private String priceDesignation;


    @Column(name = "marketplaceId")
    private String marketplaceId;

    @Column(name = "refreshtime")
    private Date refreshtime;

}
