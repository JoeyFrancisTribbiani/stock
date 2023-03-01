package com.yy.stock.adaptor.amazon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_orders_fulfilled_shipments_report")
@IdClass(OrdersFulfilledShipmentsReportID.class)
public class OrdersFulfilledShipmentsReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;


    @Id
    @Column(name = "shipment_item_id", nullable = false)
    private String shipmentItemId;

    @Id
    @Column(name = "amazon_order_item_id", nullable = false)
    private String amazonOrderItemId;

    @Column(name = "merchant_order_id")
    private String merchantOrderId;

    @Column(name = "shipment_id")
    private String shipmentId;
    @Column(name = "merchant_order_item_id")
    private String merchantOrderItemId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "payments_date")
    private Date paymentsDate;

    @Column(name = "shipment_date")
    private Date shipmentDate;

    @Column(name = "reporting_date")
    private Date reportingDate;

    @Column(name = "buyer_email")
    private String buyerEmail;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_phone_number")
    private String buyerPhoneNumber;

    @Column(name = "sku")
    private String sku;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity_shipped")
    private Integer quantityShipped;

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

    @Column(name = "ship_service_level")
    private String shipServiceLevel;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "ship_address_1")
    private String shipAddress1;

    @Column(name = "ship_address_2")
    private String shipAddress2;

    @Column(name = "ship_address_3")
    private String shipAddress3;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_state")
    private String shipState;

    @Column(name = "ship_postal_code")
    private String shipPostalCode;

    @Column(name = "ship_country")
    private String shipCountry;

    @Column(name = "ship_phone_number")
    private String shipPhoneNumber;

    @Column(name = "bill_address_1")
    private String billAddress1;

    @Column(name = "bill_address_2")
    private String billAddress2;

    @Column(name = "bill_address_3")
    private String billAddress3;

    @Column(name = "bill_city")
    private String billCity;

    @Column(name = "bill_state")
    private String billState;

    @Column(name = "bill_postal_code")
    private String billPostalCode;

    @Column(name = "bill_country")
    private String billCountry;

    @Column(name = "item_promotion_discount")
    private BigDecimal itemPromotionDiscount;

    @Column(name = "ship_promotion_discount")
    private BigDecimal shipPromotionDiscount;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "estimated_arrival_date")
    private Date estimatedArrivalDate;

    @Column(name = "fulfillment_center_id")
    private String fulfillmentCenterId;

    @Column(name = "fulfillment_channel")
    private String fulfillmentChannel;

    @Column(name = "sales_channel")
    private String salesChannel;

    @Column(name = "amazonauthid")
    private String amazonauthid;

}
