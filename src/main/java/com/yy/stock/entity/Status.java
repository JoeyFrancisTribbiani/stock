package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * stock record from supplier
 */
@Data
@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "stock_time", nullable = false)
    private LocalDateTime stockTime;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "shipment")
    private String shipment;

    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "marketplace_id")
    private String marketplaceId;

    @Column(name = "amazonauth_id")
    private String amazonauthId;

}
