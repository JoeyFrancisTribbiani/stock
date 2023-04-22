package com.yy.stock.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "stock_status")
@Accessors(chain = true)
public class StockStatus {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger id;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "amazon_auth_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger amazonAuthId;

    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "order_item_id")
    private String orderItemId;
    @Column(name = "amazon_sku")
    private String amazonSku;

    @Column(name = "amazon_purchase_time")
    private Date amazonPurchaseTime;
    @Column(name = "supplier_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger supplierId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private BuyerAccount buyer;

    @Column(name = "platform_order_id")
    private String platformOrderId;
    @Column(name = "platfom_order_page_url")
    private String platformOrderPageUrl;

    @Column(name = "stock_time")
    private Date stockTime;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "shipment_track_number")
    private String shipmentTrackNumber;
    @Column(name = "shipment_track_url")
    private String shipmentTrackUrl;
    @Column(name = "last_shipment_track_time")
    private LocalDateTime lastShipmentTrackTime;
    @Column(name = "shipment")
    private String shipment;
    @Column(name = "log")
    private String log;
    @Column(name = "stockful")
    private Boolean stockful;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "last_stock_try_time")
    private LocalDateTime lastStockTryTime;
}
