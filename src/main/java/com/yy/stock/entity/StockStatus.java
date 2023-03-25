package com.yy.stock.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * stock record from supplier
 */
@Data
@Entity
@Table(name = "stock_status")
@Accessors(chain = true)
public class StockStatus implements Serializable {

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

    @Column(name = "supplier_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger supplierId;

    @Column(name = "buyer_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger buyerId;

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
    private Boolean remarks;
}
