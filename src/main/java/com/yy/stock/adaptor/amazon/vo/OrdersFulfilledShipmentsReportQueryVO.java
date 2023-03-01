package com.yy.stock.adaptor.amazon.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrdersFulfilledShipmentsReportQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String amazonOrderId;

    private String merchantOrderId;

    private String shipmentId;

    private String shipmentItemId;

    private String amazonOrderItemId;

    private String merchantOrderItemId;

    private Date purchaseDate;

    private Date paymentsDate;

    private Date shipmentDate;

    private Date reportingDate;

    private String buyerEmail;

    private String buyerName;

    private String buyerPhoneNumber;

    private String sku;

    private String productName;

    private Integer quantityShipped;

    private String currency;

    private BigDecimal itemPrice;

    private BigDecimal itemTax;

    private BigDecimal shippingPrice;

    private BigDecimal shippingTax;

    private BigDecimal giftWrapPrice;

    private BigDecimal giftWrapTax;

    private String shipServiceLevel;

    private String recipientName;

    private String shipAddress1;

    private String shipAddress2;

    private String shipAddress3;

    private String shipCity;

    private String shipState;

    private String shipPostalCode;

    private String shipCountry;

    private String shipPhoneNumber;

    private String billAddress1;

    private String billAddress2;

    private String billAddress3;

    private String billCity;

    private String billState;

    private String billPostalCode;

    private String billCountry;

    private BigDecimal itemPromotionDiscount;

    private BigDecimal shipPromotionDiscount;

    private String carrier;

    private String trackingNumber;

    private Date estimatedArrivalDate;

    private String fulfillmentCenterId;

    private String fulfillmentChannel;

    private String salesChannel;

    private String amazonauthid;

}
