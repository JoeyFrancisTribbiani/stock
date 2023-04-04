package com.yy.stock.adaptor.amazon.vo;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;


import lombok.Getter;

import lombok.Setter;
@Getter
@Setter
public class AmzOrderItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private BigInteger id;

    @NotNull(message = "amazonOrderId can not null")
    private String amazonOrderId;

    @NotNull(message = "purchaseDate can not null")
    private LocalDateTime purchaseDate;

    private LocalDateTime lastUpdatedDate;

    @NotNull(message = "orderItemId can not null")
    private String orderItemId;

    private String asin;

    @NotNull(message = "sku can not null")
    private String sku;

    private String title;

    private Integer quantityOrdered;

    private Integer quantityShipped;

    private String currency;

    private BigDecimal itemPrice;

    private BigDecimal itemTax;

    private BigDecimal shippingPrice;

    private BigDecimal shippingTax;

    private BigDecimal giftWrapPrice;

    private BigDecimal giftWrapTax;

    private BigDecimal itemPromotionDiscount;

    private BigDecimal shipPromotionDiscount;

    private String promotionIds;

    private BigDecimal CODFee;

    private BigDecimal CODFeeDiscount;

    private String giftMessageText;

    private String giftWrapLevel;

    private String conditionId;

    private String conditionSubtypeId;

    private String conditionNote;

    private LocalDateTime scheduledDeliveryStartDate;

    private LocalDateTime scheduledDeliveryEndDate;

    @NotNull(message = "amazonAuthId can not null")
    private String amazonAuthId;

    private String marketplaceId;

}
