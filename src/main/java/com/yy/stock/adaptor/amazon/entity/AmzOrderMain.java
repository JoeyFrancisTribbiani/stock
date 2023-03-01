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
import java.time.LocalDateTime;

/**
 * purchase_order_number
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "t_amz_order_main")
public class AmzOrderMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id", nullable = false,columnDefinition = "bigint")
    private BigInteger id;

    @Id
    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "seller_order_id")
    private String sellerOrderId;

    @Column(name = "replaced_orderid")
    private String replacedOrderid;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "order_status",columnDefinition = "char")
    private String orderStatus;

    @Column(name = "fulfillment_channel",columnDefinition = "char")
    private String fulfillmentChannel;

    @Column(name = "sales_channel",columnDefinition = "char")
    private String salesChannel;

    @Column(name = "order_channel",columnDefinition = "char")
    private String orderChannel;

    /**
     * 货件服务水平
     */
    @Column(name = "ship_service_level")
    private String shipServiceLevel;

    /**
     * 买家收货地址id
     */
    @Column(name = "buyer_shipping_address_id",columnDefinition = "bigint")
    private BigInteger buyerShippingAddressId;

    @Column(name = "currency",columnDefinition = "char")
    private String currency;

    /**
     * 订单的总费用
     */
    @Column(name = "order_total")
    private BigDecimal orderTotal;

    /**
     * 已配送的商品数量。
     */
    @Column(name = "numberOfItemsShipped")
    private Integer numberOfItemsShipped;

    /**
     * 未配送的商品数量。
     */
    @Column(name = "numberOfItemsUnshipped")
    private Integer numberOfItemsUnshipped;

    /**
     * COD 订单的次级付款方式
     */
    @Column(name = "paymentMethod")
    private String paymentMethod;

    /**
     * 使用同级PaymentMethod响应元素指明的次级付款方式支付的金额
     */
    @Column(name = "payment_execution_detail_item")
    private BigDecimal paymentExecutionDetailItem;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @Column(name = "buyer_name",columnDefinition = "tinyblob")
    private String buyerName;

    /**
     * 订单的配送服务级别分类。
     */
    @Column(name = "shipment_serviceLevel_category")
    private String shipmentServicelevelCategory;

    @Column(name = "fulfillment_supply_sourceid")
    private String fulfillmentSupplySourceid;

    /**
     * 卖家自定义的配送方式，属于Checkout by Amazon (CBA) 所支持的四种标准配送设置中的一种
     */
    @Column(name = "CbaDisplayableShippingLabel",columnDefinition = "char")
    private String cbaDisplayableShippingLabel;

    @Column(name = "orderType",columnDefinition = "char")
    private String orderType;

    @Column(name = "earliestShipDate")
    private LocalDateTime earliestShipDate;

    @Column(name = "latestShipDate")
    private LocalDateTime latestShipDate;

    @Column(name = "earliestDeliveryDate")
    private LocalDateTime earliestDeliveryDate;

    @Column(name = "latestDeliveryDate")
    private LocalDateTime latestDeliveryDate;

    @Column(name = "promise_response_duedate")
    private LocalDateTime promiseResponseDuedate;

    @Column(name = "isBusinessOrder")
    private Boolean isBusinessOrder;

    @Column(name = "remark")
    private String remark;

    @Column(name = "hasItem")
    private Boolean hasItem;

    @Column(name = "marketplaceId",columnDefinition = "char")
    private String marketplaceId;

    @Column(name = "amazonAuthId", nullable = false,columnDefinition = "bigint")
    private BigInteger  amazonAuthId;

}
