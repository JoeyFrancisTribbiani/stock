package com.yy.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * stock record from supplier
 */
@Data
@Entity
@Table(name = "status")
@Accessors(chain = true)
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "marketplace_id", nullable = false)
    private String marketplaceId;

    @Column(name = "amazon_order_id", nullable = false)
    private String amazonOrderId;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "stock_time")
    private Date stockTime;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "shipment")
    private String shipment;
    @Column(name = "log")
    private String log;

}
