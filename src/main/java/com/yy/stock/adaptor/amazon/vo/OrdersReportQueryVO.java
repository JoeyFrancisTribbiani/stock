package com.yy.stock.adaptor.amazon.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrdersReportQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String amazonOrderId;

    private String merchantOrderId;

    private Date purchaseDate;

    private Date lastUpdatedDate;

    private String orderStatus;

    private String fulfillmentChannel;

    private String salesChannel;

    private String orderChannel;

    private String url;

    private String shipServiceLevel;

    private String sku;

    private String asin;

    private String itemStatus;

    private Integer quantity;

    private String currency;

    private BigDecimal itemPrice;

    private BigDecimal itemTax;

    private BigDecimal shippingPrice;

    private BigDecimal shippingTax;

    private BigDecimal giftWrapPrice;

    private BigDecimal giftWrapTax;

    private BigDecimal itemPromotionDiscount;

    private BigDecimal shipPromotionDiscount;

    private String shipCity;

    private String shipState;

    private String shipPostalCode;

    private String shipCountry;

    private String promotionIds;

    private Boolean businessOrder;

    private String purchaseOrderNumber;

    private String priceDesignation;

    private String amazonAuthId;

    private String marketplaceId;

    private Date refreshtime;

}
