package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class AmzOrderItemQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger id;

    private String amazonOrderId;

    private LocalDateTime purchaseDate;

    private LocalDateTime lastUpdatedDate;

    private String orderItemId;

    private String asin;

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

    private String amazonAuthId;

    private String marketplaceId;

}
