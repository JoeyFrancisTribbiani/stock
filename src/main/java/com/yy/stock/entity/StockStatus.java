package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private BigInteger id;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "amazon_auth_id")
    private BigInteger amazonAuthId;

    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "order_item_id")
    private String orderItemId;

    @Column(name = "supplier_id")
    private BigInteger supplierId;

    @Column(name = "buyer_id")
    private BigInteger buyerId;

    @Column(name = "platform_order_id")
    private String platformOrderId;

    @Column(name = "stock_time")
    private Date stockTime;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "shipment_track_number")
    private String shipmentTrackNumber;
    @Column(name = "shipment")
    private String shipment;
    @Column(name = "log")
    private String log;

}
