package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class AmzOrderMainQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger id;

    private String amazonOrderId;

    private String sellerOrderId;

    private String replacedOrderid;

    private LocalDateTime purchaseDate;

    private LocalDateTime lastUpdatedDate;

    private String orderStatus;

    private String fulfillmentChannel;

    private String salesChannel;

    private String orderChannel;


    /**
     * 货件服务水平
     */
    private String shipServiceLevel;


    /**
     * 买家收货地址id
     */
    private String buyerShippingAddressId;

    private String currency;


    /**
     * 订单的总费用
     */
    private BigDecimal orderTotal;


    /**
     * 已配送的商品数量。
     */
    private Integer numberOfItemsShipped;


    /**
     * 未配送的商品数量。
     */
    private Integer numberOfItemsUnshipped;


    /**
     * COD 订单的次级付款方式
     */
    private String paymentMethod;


    /**
     * 使用同级PaymentMethod响应元素指明的次级付款方式支付的金额
     */
    private BigDecimal paymentExecutionDetailItem;

    private String buyerEmail;

    private String buyerName;


    /**
     * 订单的配送服务级别分类。
     */
    private String shipmentServicelevelCategory;

    private String fulfillmentSupplySourceid;


    /**
     * 卖家自定义的配送方式，属于Checkout by Amazon (CBA) 所支持的四种标准配送设置中的一种
     */
    private String cbaDisplayableShippingLabel;

    private String orderType;

    private LocalDateTime earliestShipDate;

    private LocalDateTime latestShipDate;

    private LocalDateTime earliestDeliveryDate;

    private LocalDateTime latestDeliveryDate;

    private LocalDateTime promiseResponseDuedate;

    private Boolean isBusinessOrder;

    private String remark;

    private Boolean hasItem;

    private String marketplaceId;

    private String amazonAuthId;

}
