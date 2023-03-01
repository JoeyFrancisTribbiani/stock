package com.yy.stock.adaptor.amazon.dto;

import com.yy.stock.adaptor.amazon.entity.AmzOrderMain;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link AmzOrderMain} entity
 */
@Data
public class AmzOrderMainDTO implements Serializable {
    private final BigInteger id;
    private final String amazonOrderId;
    private final String sellerOrderId;
    private final String replacedOrderid;
    private final LocalDateTime purchaseDate;
    private final LocalDateTime lastUpdatedDate;
    private final String orderStatus;
    private final String fulfillmentChannel;
    private final String salesChannel;
    private final String orderChannel;
    private final String shipServiceLevel;
    private final BigInteger buyerShippingAddressId;
    private final String currency;
    private final BigDecimal orderTotal;
    private final Integer numberOfItemsShipped;
    private final Integer numberOfItemsUnshipped;
    private final String paymentMethod;
    private final BigDecimal paymentExecutionDetailItem;
    private final String buyerEmail;
    private final String buyerName;
    private final String shipmentServicelevelCategory;
    private final String fulfillmentSupplySourceid;
    private final String cbaDisplayableShippingLabel;
    private final String orderType;
    private final LocalDateTime earliestShipDate;
    private final LocalDateTime latestShipDate;
    private final LocalDateTime earliestDeliveryDate;
    private final LocalDateTime latestDeliveryDate;
    private final LocalDateTime promiseResponseDuedate;
    private final Boolean isBusinessOrder;
    private final String remark;
    private final Boolean hasItem;
    private final String marketplaceId;
    private final BigInteger amazonAuthId;
}