package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class OrdersReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private String id;

    @NotNull(message = "amazonOrderId can not null")
    private String amazonOrderId;

    private String merchantOrderId;

    @NotNull(message = "purchaseDate can not null")
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

    @NotNull(message = "amazonAuthId can not null")
    private String amazonAuthId;

    private String marketplaceId;

    private Date refreshtime;

}
